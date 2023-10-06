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
    private String site;

    @CommandLine.Option(
            names = {"-t", "--title"},
            required = true,
            description = "easy to remember title",
            paramLabel = "<title>")
    private String title;

    @CommandLine.Option(
            names = "--strong",
            required = false,
            description = "enter 1 for strong(16 character) password and 0 for weaker(8 character) password",
            paramLabel = "<isStrong>")
    private Integer strong;

    @CommandLine.Option(
            names = {"-c", "--custom"},
            required = false,
            interactive = true,
            echo = false,
            description = "enter desired password",
            paramLabel = "<custom password>")
    private String customPass;

    @Override
    public Integer call() throws Exception {
        if (strong != null && customPass != null) {
            // Both options specified
            System.out.println("Specify either --strong or --custom, but not both.");
            return 1;
        }

        if (strong != null && customPass == null) {
            passwordEntity.setSite(site);
            passwordEntity.setTitle(title);
            passwordEntity.setStrong(strong);
            entityService.createNewEntity(passwordEntity);
        }

        if (customPass != null && strong == null) {
            passwordEntity.setSite(site);
            passwordEntity.setTitle(title);
            passwordEntity.setPassword(customPass);
            entityService.createNewEntityWithCustomPass(passwordEntity);
        }

        if (strong == null && customPass == null) {
            passwordEntity.setSite(site);
            passwordEntity.setTitle(title);
            passwordEntity.setStrong(0);
            entityService.createNewEntity(passwordEntity);
        }
        return 0;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer isStrong() {
        return strong;
    }

    public void setStrong(Integer strong) {
        this.strong = strong;
    }

    public String getCustomPass() {
        return customPass;
    }

    public void setCustomPass(String customPass) {
        this.customPass = customPass;
    }
}
