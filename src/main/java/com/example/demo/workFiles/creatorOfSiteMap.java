package com.example.demo.workFiles;
import com.example.demo.model.Page;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class creatorOfSiteMap extends RecursiveTask<Set<Page>> {
    private Page page;
    public creatorOfSiteMap(Page page) {
        this.page = page;
    }

    @Override
    protected Set<Page> compute() {
        Set<Page> pages = new HashSet<>();
        pages.add(page);

        List<creatorOfSiteMap> taskList = new ArrayList<>();
        for (Page child : PageWrapper.getChildrenPage(page.getPath())) {
            creatorOfSiteMap task = new creatorOfSiteMap(child);

            task.fork();
            taskList.add(task);
        }
        for (creatorOfSiteMap task : taskList) {
            pages.addAll(task.join());
        }

        return pages;
    }
}

