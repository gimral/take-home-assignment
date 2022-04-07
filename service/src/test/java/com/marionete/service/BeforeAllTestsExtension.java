package com.marionete.service;

import com.marionete.backends.AccountInfoMock;
import com.marionete.backends.UserInfoMock;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

//https://stackoverflow.com/a/51556718
public class BeforeAllTestsExtension
        implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

    private static boolean started = false;
    // Gate keeper to prevent multiple Threads within the same routine
    final static Lock lock = new ReentrantLock();

    @Override
    public void beforeAll(final ExtensionContext context)  {
        // lock the access so only one Thread has access to it
        lock.lock();
        if (!started) {
            started = true;
            // The following line registers a callback hook when the root test context is
            // shut down
            context.getRoot().getStore(GLOBAL).put("BeforeAll", this);

            UserInfoMock.start();
            AccountInfoMock.start();

        }
        // free the access
        lock.unlock();
    }

    @Override
    public void close() {
        // Your "after all tests" logic goes here
    }
}
