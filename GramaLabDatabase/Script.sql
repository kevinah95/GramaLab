insert into Complete(Sentence, Word, Answer)
values 
("Rodrigo [Verbo] todo el día", "Verbo/Presente: Correr", "corre"),
("Carlos está [Verbo] en este momento", "Verbo/Gerundio: Nadar", "nadando"),
("Tienen que [Verbo] mucho para este examen","Verbo/Infinitivo: Estudiar","estudiar"),
("Fabián [Verbo] mucho la fiesta", "Verbo/Presente: Disfrutar", "disfruta"),
("Jasson [Verbo] muy poco ayer","Verbo/Pasado: Dormir","durmió");


insert into Correct(Sentence, BadWord, CorrectWord)
values
("Dicen que va a [] mucha comida", "haber", "a ver"),
("Vamos para []", "allá", "haya"),
("Cuando llegue pregunte si [] sal", "ay", "hay");

insert into Divide(Word, DividedWord, WordLen)
values
("atleta", "a tle ta", 3),
("corazón", "co ra zón", 3),
("módulo","mó du lo", 3),
("camisa", "ca mi sa", 3),
("pregunta", "pre gun ta", 3),
("escuela","es cue la", 3);

insert into Identify(Word, Type)
values
("Correr", "Verbo"),
("Casa", "Sustantivo"),
("El","Articulo"),
("Nadar", "Verbo"),
("Laptop", "Sustantivo"),
("Ella","Articulo"),
("Comer", "Verbo"),
("Mariana", "Sustantivo"),
("Ellos","Articulo"),
("Caminar", "Verbo"),
("Carro", "Sustantivo"),
("Ellas","Articulo");

insert into OrderSentence(CorrectSentence)
values
("Dicen que hay mucha comida"),
("Vamos para allá"),
("Pregunte si hay sal"),
("Estamos programando mucho"),
("Ese hombre se enojó"),
("Me gusta mucho esta fiesta");
