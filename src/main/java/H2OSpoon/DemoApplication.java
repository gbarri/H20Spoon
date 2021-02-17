package H2OSpoon;

import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.prediction.BinomialModelPrediction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	private static String modelClassName = "H2OSpoon.glm_binomial_prova_pros_retrain";

	CommandLineRunner method() {
		return args -> {
			hex.genmodel.GenModel rawModel;
			glm_binomial_prova_pros_retrain.log_rescale(new double[0]);
			rawModel = (hex.genmodel.GenModel) Class.forName(modelClassName).newInstance();
			EasyPredictModelWrapper model = new EasyPredictModelWrapper(rawModel);

			RowData row = new RowData();
			row.put("CAPSULE", "0");
			row.put("AGE", "62");
			row.put("RACE", "1");
			row.put("DPROS", "1");
			row.put("DCAPS", "1");
			row.put("PSA", "2.8");
			row.put("VOL", "44.0");
			row.put("GLEASON", "6");

			BinomialModelPrediction p = model.predictBinomial(row);
			System.out.println("Label (aka prediction) is flight departure delayed: " + p.label);
			System.out.print("Class probabilities: ");
			for (int i = 0; i < p.classProbabilities.length; i++) {
				if (i > 0) {
					System.out.print(",");
				}
				System.out.print(p.classProbabilities[i]);
			}
			System.out.println("");
		};
	}

}
