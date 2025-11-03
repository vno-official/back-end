package com.vno.organization.mapper;

import com.vno.organization.dto.OrganizationRequest;
import com.vno.organization.dto.OrganizationResponse;
import com.vno.organization.model.Organization;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-02T23:44:42+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class OrganizationMapperImpl implements OrganizationMapper {

    @Override
    public Organization toEntity(OrganizationRequest organizationRequest) {
        if ( organizationRequest == null ) {
            return null;
        }

        Organization.OrganizationBuilder organization = Organization.builder();

        organization.description( organizationRequest.getDescription() );
        organization.logoUrl( organizationRequest.getLogoUrl() );
        organization.name( organizationRequest.getName() );
        organization.status( organizationRequest.getStatus() );
        organization.websiteUrl( organizationRequest.getWebsiteUrl() );

        return organization.build();
    }

    @Override
    public OrganizationResponse toResponse(Organization organization) {
        if ( organization == null ) {
            return null;
        }

        OrganizationResponse.OrganizationResponseBuilder organizationResponse = OrganizationResponse.builder();

        organizationResponse.members( mapMembers( organization.getMembers() ) );
        organizationResponse.createdAt( organization.getCreatedAt() );
        organizationResponse.createdBy( organization.getCreatedBy() );
        organizationResponse.description( organization.getDescription() );
        organizationResponse.id( organization.getId() );
        organizationResponse.logoUrl( organization.getLogoUrl() );
        organizationResponse.name( organization.getName() );
        organizationResponse.status( organization.getStatus() );
        organizationResponse.updatedAt( organization.getUpdatedAt() );
        organizationResponse.websiteUrl( organization.getWebsiteUrl() );

        return organizationResponse.build();
    }

    @Override
    public List<OrganizationResponse> toResponseList(List<Organization> organizations) {
        if ( organizations == null ) {
            return null;
        }

        List<OrganizationResponse> list = new ArrayList<OrganizationResponse>( organizations.size() );
        for ( Organization organization : organizations ) {
            list.add( toResponse( organization ) );
        }

        return list;
    }

    @Override
    public void updateEntity(OrganizationRequest organizationRequest, Organization organization) {
        if ( organizationRequest == null ) {
            return;
        }

        if ( organizationRequest.getDescription() != null ) {
            organization.setDescription( organizationRequest.getDescription() );
        }
        if ( organizationRequest.getLogoUrl() != null ) {
            organization.setLogoUrl( organizationRequest.getLogoUrl() );
        }
        if ( organizationRequest.getName() != null ) {
            organization.setName( organizationRequest.getName() );
        }
        if ( organizationRequest.getStatus() != null ) {
            organization.setStatus( organizationRequest.getStatus() );
        }
        if ( organizationRequest.getWebsiteUrl() != null ) {
            organization.setWebsiteUrl( organizationRequest.getWebsiteUrl() );
        }
    }
}
