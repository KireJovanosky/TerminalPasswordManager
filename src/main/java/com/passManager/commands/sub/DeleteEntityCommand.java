package com.passManager.commands.sub;

import com.passManager.service.EntityService;
import picocli.CommandLine;

import java.util.concurrent.Callable;
@CommandLine.Command(name = "delete",
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        description = "Sub Command to delete password",
        requiredOptionMarker = '*',
        optionListHeading = "%nPlease choose one of the following options to delete a record:%n")
public class DeleteEntityCommand implements Callable<Integer> {

    public EntityService entityService = new EntityService();


    @CommandLine.Option(
            names = {"-s", "--site"},
            required = false,
            description = "web address of the login site you want to delete the password for",
            paramLabel = "<site name>")
    private String site;

    @CommandLine.Option(
            names = {"-t", "--title"},
            required = false,
            description = "title of the site you want to delete the password for",
            paramLabel = "<title>")
    private String title;

    @CommandLine.Option(
            names = "--id",
            required = false,
            description = "id of the site you want to delete the password for",
            paramLabel = "<id>")
    private Integer id;

    @Override
    public Integer call() {
        if (site != null && title == null && id == null) {
            entityService.deleteEntity("site", site);
        } else if (title != null && site == null && id == null) {
            entityService.deleteEntity("title", title);
        } else if (id != null && site == null && title == null) {
            entityService.deleteEntity("id", String.valueOf(id));
        } else {
            System.out.println("Please choose only one field type to delete a password record");
            return 1;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
