package com.mercosur.dax_api.walker_engine;


import com.mercosur.dax_api.WebWalker;

public interface Loggable {

    enum Level {
        VERBOSE,
        INFO,
        SEVERE
    }

    String getName();

    default void log(CharSequence debug){
        if (!WebWalker.isLogging()){
            return;
        }
        System.out.println("[" + getName() + "] " + debug);
    }

    default void log(Level level, CharSequence debug){
        if (!WebWalker.isLogging()){
            return;
        }
        System.out.println(level + " [" + getName() + "] " + debug);
    }
}
