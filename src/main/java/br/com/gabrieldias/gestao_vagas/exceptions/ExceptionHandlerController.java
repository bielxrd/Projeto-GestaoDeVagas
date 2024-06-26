package br.com.gabrieldias.gestao_vagas.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice // Serve para dizer ao Spring que sera executada antes da requisicao cair na controller e fazer o tratamento de excecoes
public class ExceptionHandlerController {

    @Autowired
    private MessageSource messageSource;


    @ExceptionHandler(MethodArgumentNotValidException.class) // Serve para dizer ao Spring que este metodo sera executado quando ocorrer uma excecao do tipo MethodArgumentNotValidException, e se acontecer, ele ira entrar no metodo e fazer o tratamento
    public ResponseEntity<List<ErrorMessageDTO>> handleMehodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ErrorMessageDTO> dto = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(err -> {
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
            ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(message, err.getField());
            dto.add(errorMessageDTO);
        });

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CandidateNotFoundException.class)
    public ResponseEntity handleCandidateNotFoundException(CandidateNotFoundException candidateNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(candidateNotFoundException.getMessage());
    }

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<Object> handleCompanyNotFoundException(CompanyNotFoundException companyNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(companyNotFoundException.getMessage());
    }


}
