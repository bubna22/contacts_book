package com.bubna.dao.cmd;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class CmdConfig {

    @Bean("createCommand")
    @Scope("prototype")
    public Command createCommand() {return new CreateEntityCommand("createCommand");}

    @Bean("listCommand")
    @Scope("prototype")
    public Command listCommand() {return new ListEntityCommand("listCommand");}

    @Bean("modifyCommand")
    @Scope("prototype")
    public Command updateCommand() {return new ModifyEntityCommand("modifyCommand");}

    @Bean("deleteCommand")
    @Scope("prototype")
    public Command deleteCommand() {return new RemoveEntityCommand("deleteCommand");}

    @Bean("inactiveUsersCommand")
    @Scope("prototype")
    public Command inactiveUsersCommand() {return new InactiveUsersCommand("inactiveUsersCommand");}

    @Bean("userContactsAVGCountCommand")
    @Scope("prototype")
    public Command userContactsAVGCountCommand() {return new UserContactsAVGCountCommand("userContactsAVGCountCommand");}

    @Bean("userGroupsAVGCountCommand")
    @Scope("prototype")
    public Command userGroupsAVGCountCommand() {return new UserGroupsAVGCountCommand("userGroupsAVGCountCommand");}

    @Bean("userContactsCountCommand")
    @Scope("prototype")
    public Command userContactsCountCommand() {return new UserContactsCountCommand("userContactsCountCommand");}

    @Bean("userGroupsCountCommand")
    @Scope("prototype")
    public Command userGroupsCountCommand() {return new UserGroupsCountCommand("userGroupsCountCommand");}

    @Bean("userCountCommand")
    @Scope("prototype")
    public Command userCountCommand() {return new UserCountCommand("countCommand");}

}
