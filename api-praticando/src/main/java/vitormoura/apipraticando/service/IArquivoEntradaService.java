package vitormoura.apipraticando.service;

import org.springframework.plugin.core.Plugin;


public interface IArquivoEntradaService<T,K> extends Plugin<K> {

    void processaArquivo (T object);

    //K getStrategyType();

}
