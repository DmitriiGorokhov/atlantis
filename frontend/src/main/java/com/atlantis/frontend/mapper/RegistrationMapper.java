package com.atlantis.frontend.mapper;

import com.atlantis.frontend.model.RegistrationRq;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RegistrationMapper {

    RegistrationMapper MAPPER = Mappers.getMapper(RegistrationMapper.class);

    com.atlantis.backend.service.registration.dto.RegistrationRq toRq(RegistrationRq rq);
}