package com.github.andercamargo.financial.infrastructure.wallet.validation;

import com.github.andercamargo.financial.infrastructure.common.validation.formats.Name;
import com.github.andercamargo.financial.infrastructure.config.db.repository.CustomerRepository;

import com.github.andercamargo.financial.infrastructure.wallet.dto.WalletInputDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueWalletValidator implements ConstraintValidator<Name, WalletInputDTO> {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public boolean isValid(WalletInputDTO walletInputDTO, ConstraintValidatorContext constraintValidatorContext) {
       if(customerRepository != null){
           return customerRepository.findByUserNameAndWallet(walletInputDTO.customerName(), walletInputDTO.name()) == null;
       }
       return false;
    }
}
