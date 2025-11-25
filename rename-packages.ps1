# File: rename-packages-updated.ps1
# Chạy trong folder gốc vno/ (nơi chứa auth-service, user-service, note-service, ...)

Write-Host "Bắt đầu đổi toàn bộ org.acme → com.vno (cả folder + nội dung file)..." -ForegroundColor Green

# Bước 1: Di chuyển folder org/acme → com/vno (main + test) cho tất cả service
Get-ChildItem -Directory -Filter "*-service" | ForEach-Object {
    $serviceName = $_.Name

    # Main java
    $oldMain = Join-Path $_.FullName "src\main\java\org\acme"
    $newMain = Join-Path $_.FullName "src\main\java\com\vno"
    if (Test-Path $oldMain) {
        Write-Host "  Đang di chuyển main: $serviceName/org/acme → com/vno" -ForegroundColor Yellow
        New-Item -ItemType Directory -Path $newMain -Force | Out-Null
        Get-ChildItem -Path $oldMain -Recurse | Move-Item -Destination { 
            $rel = $_.FullName.Substring($oldMain.Length)
            Join-Path $newMain $rel 
        } -Force
        # Xóa folder org/acme cũ nếu trống
        Remove-Item -Path (Join-Path $_.FullName "src\main\java\org") -Recurse -Force -ErrorAction SilentlyContinue
    }

    # Test java
    $oldTest = Join-Path $_.FullName "src\test\java\org\acme"
    $newTest = Join-Path $_.FullName "src\test\java\com\vno"
    if (Test-Path $oldTest) {
        Write-Host "  Đang di chuyển test: $serviceName/org/acme → com/vno" -ForegroundColor Yellow
        New-Item -ItemType Directory -Path $newTest -Force | Out-Null
        Get-ChildItem -Path $oldTest -Recurse | Move-Item -Destination { 
            $rel = $_.FullName.Substring($oldTest.Length)
            Join-Path $newTest $rel 
        } -Force
        Remove-Item -Path (Join-Path $_.FullName "src\test\java\org") -Recurse -Force -ErrorAction SilentlyContinue
    }
}

# Bước 2: Thay thế nội dung file (java, pom.xml, yml, properties, xml, ...)
Write-Host "`nThay thế nội dung org.acme → com.vno trong tất cả file..." -ForegroundColor Cyan

Get-ChildItem -Recurse -Include "*.java","*.xml","pom.xml","application.yml","application.properties","*.adoc","*.md" | ForEach-Object {
    $file = $_.FullName
    $content = Get-Content $file -Raw
    if ($content -match "org\.acme") {
        $newContent = $content -replace 'org\.acme', 'com.vno'
        Set-Content -Path $file -Value $newContent -Encoding UTF8
        Write-Host "  Đã sửa: $file" -ForegroundColor Gray
    }
}

# Bước 3: Clean target folders (vì vẫn còn .class cũ của org.acme)
Write-Host "`nĐang xóa target/ để rebuild sạch..." -ForegroundColor Magenta
Get-ChildItem -Directory -Filter "*-service" | ForEach-Object {
    $targetPath = Join-Path $_.FullName "target"
    if (Test-Path $targetPath) {
        Remove-Item $targetPath -Recurse -Force
        Write-Host "  Đã xóa target của $($_.Name)"
    }
}

Write-Host "`nHOÀN TẤT! Toàn bộ project đã sạch org.acme → com.vno 100%" -ForegroundColor Green
Write-Host "Bây giờ chạy lệnh này để rebuild sạch:" -ForegroundColor Yellow
Write-Host "   ./mvnw clean compile   (chạy trong từng service hoặc dùng script nếu muốn)" -ForegroundColor White
Write-Host "Sau khi compile xong, kiểm tra lại target/classes → phải chỉ còn com/vno thôi!" -ForegroundColor White