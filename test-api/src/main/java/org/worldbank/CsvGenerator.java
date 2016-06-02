package org.worldbank;

import org.worldbank.models.CountryData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Taylan on 1.06.2016.
 */
public class CsvGenerator {

    public static void generateCsvFile(List<CountryData> countryDatas) {
        try {
            String subProjectDirectory = System.getProperties().get("user.dir").toString();
            String projectDirectory = subProjectDirectory.substring(0, subProjectDirectory.indexOf("worldbanktests"));
            FileWriter writer = new FileWriter(projectDirectory + "countryReport.csv");

            writer.append("Country,");
            writer.append("GDP,");
            writer.append("Population,");
            writer.append("CO2 Emitions\n");

            for (CountryData countryData : countryDatas) {
                writer.append(countryData.getCountryName());
                writer.append(",");
                writer.append(countryData.getGdp());
                writer.append(",");
                writer.append(countryData.getPopulation());
                writer.append(",");
                writer.append(countryData.getCo2());
                writer.append("\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
