package com.user.springbootcase.business.abstracts;

import java.util.List;

import com.user.springbootcase.api.dto.MaxUserDto;
import com.user.springbootcase.core.utilities.concretes.DataResult;

public interface MaxUserService {
	
	DataResult<MaxUserDto> findByUserName(String username);
	DataResult<MaxUserDto> save(MaxUserDto userDto);
	DataResult<List<MaxUserDto>> findAll();

}
