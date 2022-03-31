package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.dto.SlideRequestDto;
import com.alkemy.ong.dto.SlideResponseDto;
import com.alkemy.ong.entity.Organization;
import com.alkemy.ong.entity.Slide;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.AmazonService;
import com.alkemy.ong.service.SlideService;
import com.alkemy.ong.utils.CustomMultipartFile;
import com.alkemy.ong.utils.Mapper;
import com.alkemy.ong.utils.SlideMapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SlideServiceImpl implements SlideService {
    @Autowired
    private SlideRepository slideRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private AmazonService amazonService;
    @Autowired
    private SlideMapper slideMapper;


    @Bean
    public SlideMapper slideMapper(){return new SlideMapper();}

    @Override
    public void createSlide(SlideRequestDto slideRequestDto) throws Exception {

        Slide slide = new Slide();
        Integer dOrder;
        if (slideRequestDto.getOrder()==null){
              dOrder= Math.toIntExact(slideRepository.count()+1);
              slide.setOrder(dOrder);
        }else {
            slide.setOrder(slideRequestDto.getOrder());
        }
        /*
        * Transform Base64 to MultipartFile, send to AmazonService*/
        String nameFile = "Slide "+slide.getOrder();
        MultipartFile imgSend= this.base64ToImage(slideRequestDto.getImgUrl(),nameFile);
        String amazonUrl=amazonService.uploadFile(imgSend);
        /*
        * find organization to add to slide*/
        Optional<Organization> orgFind = organizationRepository.findById(slideRequestDto.getOrganizationId());
        if (!orgFind.isPresent()){
            throw new Exception("Organization not found in Slide creation");
        }else {
            slide.setOrganizationId(orgFind.get());
        }

        slide.setImageUrl(amazonUrl);
        slide.setText(slideRequestDto.getText());

        slideRepository.save(slide);
    }

    @Override
    public SlideResponseDto getSlideDetails(String id) throws Exception {

        Optional<Slide> find = slideRepository.findById(id);
        if (!find.isPresent()){
            throw new Exception("Slide not found");
        }
        Slide entity =find.get();
        SlideResponseDto dto = slideMapper.fullSlideToDto(entity);

        return dto;
    }

    @Override
    public List<SlideDto> findAll() throws Exception {
        List<Slide> slides = slideRepository.findAll();
        if(slides.isEmpty()){
            throw new Exception("List Slide is empty");
        }
        List<SlideDto> dtos = new ArrayList<>();
        for(Slide entity : slides){
            dtos.add(Mapper.mapToDto(entity,new SlideDto()));
        }
        return dtos;
    }

    public MultipartFile base64ToImage(String encoded, String fileName) {

        // We need to remove "data:image/jpeg;base64", just to keep the bytes to decode
        String trimmedEncodedImage = encoded.substring(encoded.indexOf(",") + 1);

        byte[] decodedBytes = Base64.decodeBase64(trimmedEncodedImage);

        CustomMultipartFile customMultipartFile = new CustomMultipartFile(decodedBytes, fileName);

        try {
            customMultipartFile.transferTo(customMultipartFile.getFile());
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException : " + e);
        } catch (IOException e) {
            System.out.println("IOException : " + e);
        }

        return customMultipartFile;
    }
}
