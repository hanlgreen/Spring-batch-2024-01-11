package com.ll.sbb20240111;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class BatchExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchExampleApplication.class, args);
    }
}

@Component
public class HelloWorldBatch {

    private final JobLauncher jobLauncher;
    private final Job helloWorldJob;

    @Autowired
    public HelloWorldBatch(JobLauncher jobLauncher, Job helloWorldJob) {
        this.jobLauncher = jobLauncher;
        this.helloWorldJob = helloWorldJob;
    }

    public void runBatchJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        JobExecution jobExecution = jobLauncher.run(helloWorldJob, jobParameters);
        System.out.println("배치 작업 상태: " + jobExecution.getStatus());
    }
}

@SpringBootApplication
public class BatchExampleApplication implements CommandLineRunner {

    private final HelloWorldBatch helloWorldBatch;

    @Autowired
    public BatchExampleApplication(HelloWorldBatch helloWorldBatch) {
        this.helloWorldBatch = helloWorldBatch;
    }

    public static void main(String[] args) {
        SpringApplication.run(BatchExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        helloWorldBatch.runBatchJob();
    }
}
