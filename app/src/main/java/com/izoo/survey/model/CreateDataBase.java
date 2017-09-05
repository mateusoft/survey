package com.izoo.survey.model;

/**
 * Created by mateusz on 11.08.17.
 */

public class CreateDataBase {

    public String addTypeUser = "Insert into Type_User values (1,'User'), (2,'Employee'), (3,'Administrator')";
    public String addUser = "Insert into User values (1,'User','abc',1),(2,'Employee','abc',2),(3,'Administrator','abc',3), (4,'User1','abc',1), (5,'User2','abc',1)";
    public String addSurvey = "Insert into Survey values (1,'Ankieta Konsumencka','2017-08-10 01:25:00','2017-08-10 01:25:00',0), "+
            "(2,'Ankieta Anonimowa','2017-08-12 01:25:00','2017-08-12 01:25:00',1)";
    public String addSection = "Insert into Section values (1,'Informacje o respondencie'),(2,'Ankieta'), (3,'Zwierzeta'), (4,'Rosliny')";
    public String addTypeAnswer = "Insert into Type_Question values (1,'Multiple'),(2,'Single'),(3,'Open')";
    public String addQuestion = "Insert into Question (Text_Question,Required,Tips,ID_Type_Question) values ('Płeć:',1,'Podaj płeć',2), "+
            "('Wykształcenie:',1,'Podaj wykształcenie',2), ('Wiek:',0,'Niewymagane',2), " +
    "('Województwo:',0,'brak',2), ('Miejsce zamieszkania:',1,'',2), ('Dochody netto na osobę w gospodarstwie domowym:',1,'',2), "+
            "('Ulubione zwierze:',1,'Wybierz kilka',1), " +
            "('Masz zwierze?:',1,'Napisz jakie',3), ('Zwierze :',1,'Single',2), ('Lala:',0,'Niewymagne',2), " + "('Ulubiona roslina:',1,'Wybierz kilka',1), " +
            "('Lubisz kwiaty?:',1,'Napisz jakie',3)";


    String addAnswers = "Insert Into Answers_To_Question (Text_Answer,Sequence,Has_text,ID_Question) values ('Kobieta',1,0,1), ('Mężczyzna',2,0,1), "+
            "('Podstawowe',1,0,2), ('Zawodowe',3,0,2), ('Średnie',2,0,2), " +
            "('Wyższe',4,0,2), ('Inne',5,1,2), ('do 20 lat',1,0,3), ('20 - 30 lat',2,0,3), " +
            "('30 - 40 lat',3,0,3), ('40 - 50 lat',5,0,3), ('powyżej 50 lat',4,0,3),('Podaj dokaldny wiek',6,1,3), ('dolnośtląskie',1,0,4), " +
            "('kujawsko-pomorkie',2,0,4), ('lubelskie',3,0,4), ('lubuskie',4,0,4), ('łódzkie',5,0,4), " +
            "('małopolskie',6,0,4), ('mazowieckie',7,0,4), ('opolskie',8,0,4), ('podkarpackie',9,0,4), " +
            "('podlaskie',10,0,4), ('pomorskie',11,0,4), ('śląskie',12,0,4), ('świętokrzyskie',13,0,4), " +
            "('warmińsko-mazurskie',14,0,4), ('wielkopolskie',16,0,4), ('zachodniopomoskie',15,0,4), " +
            "('Wieś',1,0,5), ('Miasto do 50 tys. mieszkańców',2,0,5), ('Miasto od 51 tys do 150 tys. mieszkańców',3,0,5), " +
            "('Miasto od 151 tys do 250 tys. mieszkańców',4,0,5), ('Miasto od 251 tys do 500 tys. mieszkańców',5,0,5), " +
            "('Miasto powyzej 500 tys. mieszkańców',6,0,5), ('Podaj miejscowosc',7,1,5), ('Poniżej 1500 zł',1,0,6), ('1500 - 2500 zł',3,0,6), " +
            "('Powyżej 2500 zł',2,0,6), ('Podaj kwote',4,1,6), ('Krowa',1,0,7), ('Owca',2,0,7), ('Pies',3,0,7), ('Koń',4,0,7), ('Królik',5,0,7), ('Napisz jakie',6,1,7), "+
            "('Napisz esej',1,1,8), ('Gepard',1,0,9), ('Lala',3,1,9), ('Lew',2,0,9), "+
            " ('Tak',1,0,10), ('Nie',2,0,10), ('Cos innego',3,1,10), ('Dąb',1,0,11), ('Róża',2,0,11), ('Palma',3,0,11), ('Manuka',4,0,11), ('Kaktus',5,1,11), ('10 zdan',1,1,12)";

    String addSurveyUsers = "Insert Into Survey_User values (1,1), (1,2), (2,2), (2,1), (3,1), (4,1), (4,2)";
    String addSurveyInSection = "Insert Into Sections_In_Survey values (1,1,1), (2,1,2), (2,2,2), (3,2,3), (4,2,1)";
    String addQuestionInSection = "Insert Into Questions_In_Section values (1,1,1),(2,1,3),(3,1,2),(4,1,4),(5,1,5),(6,2,1), "+
            "(7,2,2), (7,3,2), (8,3,1), (9,3,3), (10,3,4), (11,4,1), (12,4,3), (10,4,2)";
}
