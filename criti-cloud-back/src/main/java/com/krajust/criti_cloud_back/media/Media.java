package com.krajust.criti_cloud_back.media;

import com.krajust.criti_cloud_back.common.entity.EntityExternalIdType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "media")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    public UUID id;
    public String name;
    public String posterUrl;
    @Enumerated(EnumType.STRING)
    public DetailsType detailsType;
    public UUID detailsId;
    @Enumerated(EnumType.STRING)
    public EntityExternalIdType externalIdType;
    public String externalId;

}
