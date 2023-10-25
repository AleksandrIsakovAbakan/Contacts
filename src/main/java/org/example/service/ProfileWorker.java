package org.example.service;

import org.example.service.InitContactsInterface;

public class ProfileWorker {


    private final InitContactsInterface initContactsInterface;

    public ProfileWorker(InitContactsInterface initContactsInterface) {
        this.initContactsInterface = initContactsInterface;
    }

    public void doWork(){
        initContactsInterface.getInitContactsInterface();
    }
}
