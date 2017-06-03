package com.mycompany.adserver.entities;

import com.mycompany.adserver.dtos.ImageDto;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Entity
@Table(name = "image")
public final class Image {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable=false)
    private final Long id;

    @Column(nullable=false)
    private final String url;

    @Column(nullable=false)
    private final Long width;

    @Column(nullable=false)
    private final Long height;


    public Image(String url, long width, long height) {
        if(StringUtils.isEmpty(url)){
            throw  new IllegalArgumentException("URL cannot be null");
        }
        this.url = url;
        this.id = null;
        this.width = width;
        this.height = height;
    }

    public Image(ImageDto imageDto) {
        this(imageDto.getUrl(),imageDto.getWidth(),imageDto.getHeight());
    }

    public ImageDto getImageDto(){
        ImageDto imageDto = new ImageDto();
        imageDto.setUrl(this.url);
        imageDto.setHeight(this.height);
        imageDto.setWidth(this.width);
        return imageDto;
    }

}
