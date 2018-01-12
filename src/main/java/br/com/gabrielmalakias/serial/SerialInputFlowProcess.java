package br.com.gabrielmalakias.serial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SerialInputFlowProcess {
    private final InputFlow inputFlow;

    @Autowired
    SerialInputFlowProcess(InputFlow inputFlow) {
        this.inputFlow = inputFlow;
    }

    @Async
    public void run() {
        inputFlow.start();
    }
}
