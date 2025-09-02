package com.todolist.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
//TODO -> ver se faz sentido usar o @ConditionalOnProperty e as nuances disso, o problema que precisa ser resolvido é como
// a aplicação vai rodar com @Scheduled nos momentos de testes unitarios e de integração
public class EmailSenderSchedulingService {

    //TODO DIA AS 00:00:01
    @Scheduled(cron = "1 0 0 * * *")
    void sendEmailToUsersWithExpiredPendingTasks(){

    }

    //TODO
    // Eu posso retornar so a tarefa pois a tarefa contem o usuario
    //metodo no EmailService que retorna uma lista de usuarios e tarefas que se encaixam no criterio
    //pra cada um deles é chamado um metodo que recebe o usuario, tarefa e prepara e manda o email automaticamente
    //usando o EmailSenderService emailSenderService
    //acho que faz sentido essa logica estar inteira no EmailService, se é que faz sentido existir um EmailService

}
