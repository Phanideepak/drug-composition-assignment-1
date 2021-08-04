CREATE TABLE compositions(id INT AUTO_INCREMENT,
	name VARCHAR(255) UNIQUE, 
	PRIMARY KEY(id));

CREATE TABLE ingredients(id INT AUTO_INCREMENT,
	name VARCHAR(255) UNIQUE,
    PRIMARY KEY(id)
);

CREATE TABLE molecules(id INT AUTO_INCREMENT,
   name VARCHAR(255) UNIQUE,
   rx_required BOOLEAN NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE composition_ingredients(id INT AUTO_INCREMENT,
 composition_id INT,
 ingredient_id INT,
 unit VARCHAR(40),
 strength Float,
 PRIMARY KEY(id),
 FOREIGN KEY(composition_id) REFERENCES compositions(id) ON DELETE SET NULL,
 FOREIGN KEY(ingredient_id) REFERENCES ingredients(id) ON DELETE SET NULL
);

CREATE TABLE molecule_ingredients(id INT AUTO_INCREMENT,
    molecule_id INT,
    ingredient_id INT,
    PRIMARY KEY(id),
    FOREIGN KEY(molecule_id) REFERENCES molecules(id) ON DELETE SET NULL,
    FOREIGN KEY(ingredient_id) REFERENCES ingredients(id) ON DELETE SET NULL

);

alter table molecules change rx_required rx_required BOOLEAN NOT NULL;
alter table compositions change name name TEXT unique;

insert into ingredients(name) values('aspirin');
insert into ingredients(name) values('amoxicillin');

insert into compositions(name) value('c1');
insert into compositions(name) value('c2');

insert into composition_ingredients(composition_id,ingredient_id,unit,strength) 
	   values(1,1,'MG',50), (1,3,'MG',69), (2,1,'MG',80);
insert into molecules(name) value('m1');
insert into molecules(name) value('m2');

insert into molecule_ingredients(molecule_id,ingredient_id)
		values(1,1),(1,3),(2,1);

update molecules set rx_required=true;
SET SQL_SAFE_UPDATES = 0;

select * from ingredients;
select * from molecules;
commit;

select count(ingredient_id) from composition_ingredients group by composition_id;

select name as ingredientName,unit,strength,ingredient_id from composition_ingredients
 join ingredients on ingredient_id=ingredients.id where composition_id=1;


select molecule_id,group_concat(ingredient_id) from molecule_ingredients join molecules on molecule_id=molecules.id group by molecule_id;

select * from molecules join molecule_ingredients on molecules.id=molecule_ingredients.molecule_id;

select * from composition_ingredients where unit="MG" and strength=50 and ingredient_id=1;

select distinct composition_ingredients.composition_id from compositions 
   inner join composition_ingredients on compositions.id=composition_ingredients.composition_id
   inner join molecule_ingredients on composition_ingredients.ingredient_id=molecule_ingredients.ingredient_id
   inner join molecules on molecule_ingredients.molecule_id=molecules.id
   where (molecule_ingredients.ingredient_id=1 and strength=80 and molecules.rx_required=true);


/*
  SELECT * FROM compositions 
INNER JOIN composition_ingredients ON compositions.id = composition_ingredients.composition_id
INNER JOIN molecule_ingredients ON composition_ingredients.ingredient_id = molecule_ingredients.ingredient_id
INNER JOIN molecules ON molecule_ingredients.molecule_id = molecules.id
WHERE (molecule_ingredients.ingredient_id=1 AND strength=200 AND molecules.rx_required = false);
*/

/*
composition_ingredients 
id: PK
composition_id: FK to compositions.id 
ingredient_id: FK to ingredients.id 
unit: Varchar
strength: Floating

molecule_ingredients 
id: PK
molecule_id: FK to molecules.id 
ingredient_id: FK to ingredients.id
*/