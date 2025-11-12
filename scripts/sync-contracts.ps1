$services = @(
    @{name="auth-service"; port=8081; path="api/auth"}
    @{name="user-service"; port=8082; path="api/users"}
)

foreach ($svc in $services) {
    $src = "contracts/openapi/$($svc.name).yaml"
    $dest = "$($svc.name)/src/main/resources/openapi.yaml"
    Copy-Item $src -Destination $dest -Force
    Write-Host "Synced: $src → $dest"
}