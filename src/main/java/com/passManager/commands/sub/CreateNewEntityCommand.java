package com.passManager.commands.sub;

import com.passManager.entity.PasswordEntity;
import com.passManager.service.EntityService;
import picocli.CommandLine;

import java.util.concurrent.Callable;
@CommandLine.Command(name = "save",
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        description = "Sub Command to save password",
        requiredOptionMarker = '*',
        optionListHeading = "%nPlease choose one of the following options:%n")
public class CreateNewEntityCommand implements Callable<Integer> {

    PasswordEntity passwordEntity = new PasswordEntity();
    EntityService entityService = new EntityService();

    @CommandLine.Option(
            names = {"-s", "--site"},
            required = true,
            description = "web address of the login site",
            paramLabel = "<site name>")
    String site;

    @CommandLine.Option(
            names = {"-t", "--title"},
            required = true,
            description = "easy to remember title",
            paramLabel = "<title>")
    String title;

    @CommandLine.Option(
            names = "--strong",
            required = false,
            description = "enter 1 for strong(16 character) password and 0 for weaker(8 character) password",
            paramLabel = "<isStrong>")
    Integer strong;

    @CommandLine.Option(
            names = {"-c", "--custom"},
            required = false,
            interactive = true,
            echo = false,
            description = "enter desired password",
            paramLabel = "<custom password>")
    String customPass;


    @Override
    public Integer call() throws Exception {
        if (strong != null && customPass != null) {
            // Both options specified
            System.out.println("Specify either --strong or --custom, but not both.");
            return 1;
        }

        if (strong != null) {
            passwordEntity.setSite(site);
            passwordEntity.setTitle(title);
            passwordEntity.setStrong(strong);
            entityService.createNewEntity(passwordEntity);
        }

        if (customPass != null) {
            passwordEntity.setSite(site);
            passwordEntity.setTitle(title);
            passwordEntity.setPassword(customPass);
            entityService.CreateNewEntityWithCustomPass(passwordEntity);
        }

        if (strong == null) {
            passwordEntity.setSite(site);
            passwordEntity.setTitle(title);
            passwordEntity.setStrong(0);
            entityService.createNewEntity(passwordEntity);
        }


        return 0;
    }
}
