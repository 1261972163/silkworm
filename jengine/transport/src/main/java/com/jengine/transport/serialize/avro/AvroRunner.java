package com.jengine.transport.serialize.avro;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author nouuid
 * @date 4/14/2016
 * @description
 */
public class AvroRunner {

    public void generateSchema() {
        Schema schema = ReflectData.get().getSchema(AvroMessage.class);
        System.out.println(schema);
    }

    public void serializeDeserialize() {
        AvroParser avroSerializeDeserialize = new AvroParser();
        User[] users = prepareData();
        String path = AvroRunner.class.getResource("/").getPath() + "avro/users.avro";
        avroSerializeDeserialize.serialize(path, users);
        avroSerializeDeserialize.deSerialize(path);
    }

    private User[] prepareData() {
        User[] users = new User[3];

        users[0] = new User();
        users[0].setName("Alyssa");
        users[0].setFavoriteNumber(256);
        System.out.println(users[0] instanceof Serializable);

        users[1] = new User("Ben", 7, "red");

        users[2] = User.newBuilder()
                .setName("Charlie")
                .setFavoriteColor("blue")
                .setFavoriteNumber(null)
                .build();

        System.out.println(users[2].getName() + "," + users[2].getFavoriteColor());

        return users;
    }

}

class AvroMessage {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

class AvroParser {

    /**
     * serialize schema and User objects to avro/users.avro
     */
    public void serialize(String path, User[] users) {

        try {
            DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(User.class);
            DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);

            dataFileWriter.create(users[0].getSchema(), new File(path));

            for (User user : users) {
                dataFileWriter.append(user);
            }
            dataFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * deserialize objec from avro/users.avro
     */
    public void deSerialize(String path) {
        try {
            DatumReader<User> userDatumReader = new SpecificDatumReader<User>(User.class);
            DataFileReader<User> dataFileReader = new DataFileReader<User>(new File(path), userDatumReader);
            User user = null;
            while (dataFileReader.hasNext()) {
                // Reuse user object by passing it to next(). This saves us from
                // allocating and garbage collecting many objects for files with
                // many items.
                user = dataFileReader.next(user);
                System.out.println(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
