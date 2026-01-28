package edu.aitu.oop3.db;

import java.sql.*;

public class DatabaseCreation {
    public static void init(){
        vehiclesDB();
        parking_spotsDB();
        reservationDB();
        tarifsDB();
    }
    private static void vehiclesDB() {
        String sql = """
create table if not exists vehicles (
id serial primary key,
plate_number varchar(15);
);
""";
        execute(sql);
    }

    private static void reservationDB(){
        String sql= """
                create table if not exists reservation(
                id serial primary key,
                spot_id references parking_spots(id),
                vehicle_id references vehicles(id),
                start_time timestamp default current_timestamp,
                end_time timestamp
                );
                """;
        execute(sql);
    }
    private static void parking_spotsDB(){
        String sql= """
                create table if not exists parking_spots(
                id serial primary key;
                spotNumber int,
                isOccupied boolean not null
                );
                """;
        execute(sql);
    }
    private static void tarifsDB(){
        String sql= """
                create table if not exists tarifs(
                name varchar(50) not null,
                rate_Per_hour int
                );
                """;
        execute(sql);
    }
    private static void execute(String sql){
        try(Connection connection = DatabaseConnection.getConnection(); Statement st = connection.createStatement()){
            st.execute(sql);
            System.out.println("Executed:" + sql.split("\\(")[0]);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}