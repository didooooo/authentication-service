package com.tinqinacademy.authentication.restexport;

import com.tinqinacademy.authentication.api.operation.changePassword.ChangePasswordInput;
import com.tinqinacademy.authentication.api.operation.changePassword.ChangePasswordOutput;
import com.tinqinacademy.authentication.api.operation.confirmRegistration.ConfirmRegistrationInput;
import com.tinqinacademy.authentication.api.operation.confirmRegistration.ConfirmRegistrationOutput;
import com.tinqinacademy.authentication.api.operation.demoteAdmin.DemoteAdminInput;
import com.tinqinacademy.authentication.api.operation.demoteAdmin.DemoteAdminOperation;
import com.tinqinacademy.authentication.api.operation.demoteAdmin.DemoteAdminOutput;
import com.tinqinacademy.authentication.api.operation.login.LoginInput;
import com.tinqinacademy.authentication.api.operation.login.LoginOutput;
import com.tinqinacademy.authentication.api.operation.promoteUserToAdmin.PromoteUserToAdminInput;
import com.tinqinacademy.authentication.api.operation.promoteUserToAdmin.PromoteUserToAdminOutput;
import com.tinqinacademy.authentication.api.operation.recoverPassword.RecoverPasswordInput;
import com.tinqinacademy.authentication.api.operation.recoverPassword.RecoverPasswordOutput;
import com.tinqinacademy.authentication.api.operation.register.RegisterInput;
import com.tinqinacademy.authentication.api.operation.register.RegisterOutput;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.security.core.parameters.P;

@Headers({"Content-Type: application/json"})
public interface RestExportAuthentication {
    @RequestLine("POST /auth/login?input={input}")
    LoginOutput login(@Param("input") LoginInput input);

    @RequestLine("POST /auth/register?input={input}")
    RegisterOutput register(@Param("input") RegisterInput input);

    @RequestLine("POST /auth/change-password?input={input}")
    ChangePasswordOutput changePassword(@Param("input") ChangePasswordInput input);

    @RequestLine("POST /auth/recover-password?input={input}")
    RecoverPasswordOutput recoverPassword(@Param("input") RecoverPasswordInput input);

    @RequestLine("POST /auth/confirm-registration?input={input}")
    ConfirmRegistrationOutput confirmRegistration(@Param("input") ConfirmRegistrationInput input);

    @RequestLine("POST /auth/demote?input={input}")
    DemoteAdminOutput demoteAdmin(@Param("input") DemoteAdminInput input);

    @RequestLine("POST /auth/promote?input={input}")
    PromoteUserToAdminOutput promoteUserToAdmin(@Param("input") PromoteUserToAdminInput input);
}
