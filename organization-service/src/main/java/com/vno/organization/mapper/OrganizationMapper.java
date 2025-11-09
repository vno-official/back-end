package com.vno.organization.mapper;

import com.vno.organization.dto.OrganizationRequest;
import com.vno.organization.dto.OrganizationResponse;
import com.vno.organization.model.Organization;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {MemberMapper.class})
public interface OrganizationMapper {
    
    OrganizationMapper INSTANCE = Mappers.getMapper(OrganizationMapper.class);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Organization toEntity(OrganizationRequest organizationRequest);
    
    @Mapping(source = "members", target = "members", qualifiedByName = "mapMembers")
    OrganizationResponse toResponse(Organization organization);
    
    List<OrganizationResponse> toResponseList(List<Organization> organizations);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "members", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(OrganizationRequest organizationRequest, @MappingTarget Organization organization);
    
    @Named("mapMembers")
    default Set<com.vno.organization.dto.MemberResponse> mapMembers(Set<com.vno.organization.model.Member> members) {
        if (members == null) {
            return null;
        }
        return members.stream()
                .map(member -> MemberMapper.INSTANCE.toResponse(member))
                .collect(Collectors.toSet());
    }
}
