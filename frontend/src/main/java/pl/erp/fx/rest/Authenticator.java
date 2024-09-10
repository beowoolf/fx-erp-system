package pl.erp.fx.rest;

import pl.erp.fx.dto.OperatorCredentialsDto;
import pl.erp.fx.handler.AuthenticationResultHandler;

public interface Authenticator {

    void authenticate(OperatorCredentialsDto operatorCredentialsDto, AuthenticationResultHandler authenticationResultHandler);

}
