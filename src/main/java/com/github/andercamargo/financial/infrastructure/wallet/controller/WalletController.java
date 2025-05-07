package com.github.andercamargo.financial.infrastructure.wallet.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.andercamargo.financial.infrastructure.operation.dto.OperationInputDTO;
import com.github.andercamargo.financial.infrastructure.operation.dto.OperationTransferInputDTO;
import com.github.andercamargo.financial.infrastructure.wallet.dto.WalletBalanceResponseDTO;
import com.github.andercamargo.financial.infrastructure.wallet.dto.WalletInputDTO;
import com.github.andercamargo.financial.infrastructure.wallet.dto.WalletResponseDTO;
import com.github.andercamargo.financial.usecase.wallet.WalletOperationUseCase;
import com.github.andercamargo.financial.usecase.wallet.gateway.IWalletUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/api")
public class WalletController {
    private final IWalletUseCase walletUseCase;
    private final WalletOperationUseCase walletOperationUseCase;

    public WalletController(IWalletUseCase walletUseCase, WalletOperationUseCase createWalletOperationUseCase) {
        this.walletUseCase = walletUseCase;
        this.walletOperationUseCase = createWalletOperationUseCase;
    }

    @PostMapping("/wallet")
    @ResponseStatus(HttpStatus.CREATED)
    public WalletResponseDTO createWallet(@Valid @RequestBody WalletInputDTO wallet) throws Exception {
        return new WalletResponseDTO(this.walletUseCase.create(wallet));
    }

    @GetMapping("/wallet/balance")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getBalance(@Valid @NotBlank(message = "name must be non blank")
                                         @RequestParam String name,
                                     @RequestParam @NotBlank(message = "customerName must be non blank") String customerName) throws Exception {
        var walletDTO = this.walletUseCase.retrieveBalance(customerName, name);
        if(walletDTO == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet not found");
        }
        return ResponseEntity.ok(new WalletBalanceResponseDTO(walletDTO));
    }


    @PostMapping("/wallet/deposit")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity createDeposit(@Valid @RequestBody OperationInputDTO operationInputDTO) throws Exception {
        if(this.walletOperationUseCase.createDeposit(operationInputDTO)){
            return ResponseEntity.status(HttpStatus.OK).body("Deposit has been finished with success.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating deposit.");
    }


    @PostMapping("/wallet/withdraw")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity createWithdraw(@Valid @RequestBody OperationInputDTO operationInputDTO) throws Exception {
        if(this.walletOperationUseCase.createWithdraw(operationInputDTO)){
            return ResponseEntity.status(HttpStatus.OK).body("Withdraw has been finished with success.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating withdraw.");
    }


    @PostMapping("/wallet/transfer")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity createTransfer(@Valid @RequestBody OperationTransferInputDTO operationInputDTO) throws Exception {
        if(this.walletOperationUseCase.createTransfer(operationInputDTO)){
            return ResponseEntity.status(HttpStatus.OK).body("Transfer has been finished with success.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating transfer.");
    }

    @GetMapping("/wallet/balance/period")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getBalanceByDate(@Valid @NotBlank(message = "name must be non blank")
                                     @RequestParam String name,
                                     @RequestParam @NotBlank(message = "customerName must be non blank") String customerName,
                                           @RequestParam("date") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date) throws Exception {
        var walletDTO = this.walletOperationUseCase.getBalance(date, name, customerName);
        if(walletDTO == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet not found");
        }
        return ResponseEntity.ok(new WalletBalanceResponseDTO(walletDTO));
    }
}
