package dao;

import model.University;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class FileAccess {


    public static University mapDataFromFile(String path) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(University.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        System.out.println("Output from our XML File: ");
        Unmarshaller um = context.createUnmarshaller();
        File file = new File(path);
        University university = (University) um.unmarshal(file);

        return university;
    }
}
