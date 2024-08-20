package com.tinqinacademy.authentication.rest.controllers;

import com.tinqinacademy.authentication.api.mappings.URLMapping;
import com.tinqinacademy.authentication.api.operation.changePassword.ChangePasswordInput;
import com.tinqinacademy.authentication.api.operation.changePassword.ChangePasswordOperation;
import com.tinqinacademy.authentication.api.operation.confirmRegistration.ConfirmRegistrationInput;
import com.tinqinacademy.authentication.api.operation.confirmRegistration.ConfirmRegistrationOperation;
import com.tinqinacademy.authentication.api.operation.demoteAdmin.DemoteAdminInput;
import com.tinqinacademy.authentication.api.operation.demoteAdmin.DemoteAdminOperation;
import com.tinqinacademy.authentication.api.operation.promoteUserToAdmin.PromoteUserToAdminInput;
import com.tinqinacademy.authentication.api.operation.promoteUserToAdmin.PromoteUserToAdminOperation;
import com.tinqinacademy.authentication.api.operation.recoverPassword.RecoverPasswordInput;
import com.tinqinacademy.authentication.api.operation.recoverPassword.RecoverPasswordOperation;
import com.tinqinacademy.authentication.api.operation.register.RegisterInput;
import com.tinqinacademy.authentication.api.operation.register.RegisterOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController extends BaseController {
    private final RegisterOperation registerOperation;
    private final RecoverPasswordOperation recoverPasswordOperation;
    private final ConfirmRegistrationOperation confirmRegistrationOperation;
    private final ChangePasswordOperation changePasswordOperation;
    private final PromoteUserToAdminOperation promoteUserToAdminOperation;
    private final DemoteAdminOperation demoteAdminOperation;

    public AuthenticationController(RegisterOperation registerOperation, RecoverPasswordOperation recoverPasswordOperation, ConfirmRegistrationOperation confirmRegistrationOperation, ChangePasswordOperation changePasswordOperation, PromoteUserToAdminOperation promoteUserToAdminOperation, DemoteAdminOperation demoteAdminOperation) {
        this.registerOperation = registerOperation;
        this.recoverPasswordOperation = recoverPasswordOperation;
        this.confirmRegistrationOperation = confirmRegistrationOperation;
        this.changePasswordOperation = changePasswordOperation;
        this.promoteUserToAdminOperation = promoteUserToAdminOperation;
        this.demoteAdminOperation = demoteAdminOperation;
    }
    @PostMapping(URLMapping.REGISTER)
    public ResponseEntity<?> register(@RequestBody RegisterInput input){
        return handleTheEither(registerOperation.process(input));
    }
    @PostMapping(URLMapping.RECOVER_PASSWORD)
    public ResponseEntity<?> recoverPassword(@RequestBody RecoverPasswordInput input){
        return handleTheEither(recoverPasswordOperation.process(input));
    }
    @PostMapping(URLMapping.CONFIRM_REGISTER)
    public ResponseEntity<?> confirmRegistration(@RequestBody ConfirmRegistrationInput input){
        return handleTheEither(confirmRegistrationOperation.process(input));
    }
    @PostMapping(URLMapping.CHANGE_PASSWORD)
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordInput input){
        return handleTheEither(changePasswordOperation.process(input));
    }
    @PostMapping(URLMapping.PROMOTE)
    public ResponseEntity<?> promoteUserToAdmin(@RequestBody PromoteUserToAdminInput input){
        return handleTheEither(promoteUserToAdminOperation.process(input));
    }
    @PostMapping(URLMapping.DEMOTE)
    public ResponseEntity<?> demoteAdmin(@RequestBody DemoteAdminInput input){
        return handleTheEither(demoteAdminOperation.process(input));
    }
}
