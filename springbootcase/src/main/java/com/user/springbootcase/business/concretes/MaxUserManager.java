package com.user.springbootcase.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.user.springbootcase.api.dto.MaxUserDto;
import com.user.springbootcase.business.abstracts.MaxUserService;
import com.user.springbootcase.core.utilities.abstracts.UserInputValidationService;
import com.user.springbootcase.core.utilities.concretes.DataResult;
import com.user.springbootcase.dataAccess.MaxUserDao;
import com.user.springbootcase.entities.MaxUser;

@Service
public class MaxUserManager implements MaxUserService {

	private MaxUserDao userDao;
	private UserInputValidationService userInputValidationService;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public MaxUserManager(MaxUserDao userDao, UserInputValidationService userInputValidationService,
			PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.userInputValidationService = userInputValidationService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public DataResult<MaxUserDto> findByUserName(String username) {
		// TODO Auto-generated method stub
		try {
			MaxUser user = userDao.findByUserName(username);
			MaxUserDto userDto = new MaxUserDto();

			if (user != null) {
				userDto.setUserName(user.getUserName());
				userDto.setPassword(user.getPassword());
				userDto.setEmail(user.getEmail());
				userDto.setType(user.getType());
				userDto.setRole(user.getRole());
				return new DataResult<MaxUserDto>(userDto, true, username + " kullancı adlı kişi getirildi.");
			}

			return new DataResult<MaxUserDto>(false, username + " kullancı adlı kişi bulunamadı.");
		} catch (Exception e) {
			// TODO: handle exception
			return new DataResult<MaxUserDto>(false, username + e.getMessage());

		}

	}

	@Override
	public DataResult<MaxUserDto> save(MaxUserDto userDto) {
		// TODO Auto-generated method stub
		try {
			boolean emailValid = userInputValidationService.emailCheck(userDto.getEmail());
			boolean passwordValid = userInputValidationService.passwordCheck(userDto.getPassword());
			
			if (emailValid && passwordValid){
					 
				MaxUser user = new MaxUser();

				user.setUserName(userDto.getUserName());
				user.setEmail(userDto.getEmail());
				// Şifreleme yapılacak burada
				user.setPassword(passwordEncoder.encode(userDto.getPassword()).toString());

				user.setType(userDto.getType());
				user.setRole(userDto.getRole());

				// id si burada var. Sadece göstermek için ekledim
				/* MaxUser userDb = */ userDao.save(user);

				return new DataResult<MaxUserDto>(userDto, true, "Kullanıcı Veritabanına eklendi.");
			}

			if (!(emailValid)) {
				return new DataResult<MaxUserDto>(false, "Email'inizi kontrol ediniz.");
			}

			if (!(passwordValid)) {
				return new DataResult<MaxUserDto>(false, "Şifreler minimum 8 karakter maksimum 16 karakter olabilir.");
			}

			return new DataResult<MaxUserDto>(false, "Bir sorun oluştu işlem başarısız.");
		} catch (Exception e) {
			// TODO: handle exception
			return new DataResult<MaxUserDto>(false, e.getMessage());
		}

	}

	public DataResult<List<MaxUserDto>> findAll() {
		try {
			List<MaxUserDto> usersDto = new ArrayList<>();
			List<MaxUser> users = userDao.findAll();

			if (users.size() != 0) {
				users.forEach(user -> {
					MaxUserDto userDto = new MaxUserDto();
					userDto.setUserName(user.getUserName());
					userDto.setEmail(user.getEmail());
					userDto.setPassword(user.getPassword());
					userDto.setType(user.getType());
					userDto.setRole(user.getRole());
					usersDto.add(userDto);
				});
			} else {
				return new DataResult<List<MaxUserDto>>(false, "Kayıtlı kullanıcı bulunamadı.");
			}
			return new DataResult<List<MaxUserDto>>(usersDto, true, "Kayıtlı kullanıcılar getirildi.");
		} catch (Exception e) {
			// TODO: handle exception
			return new DataResult<List<MaxUserDto>>(false, e.getMessage());
		}

	}

}
