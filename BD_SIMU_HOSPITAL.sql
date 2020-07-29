			
			/*BANCO DE DADOS*/
CREATE DATABASE HOSPITAL_MODEL;


			/*TABELA (DESC)*/
CREATE TABLE PACIENTE(
	IDPACIENTE INT PRIMARY KEY NOT NULL,
	CLASSIFICACAO VARCHAR(10),
	C_EXAME ENUM('1','0'),
	C_MEDICAMENTO ENUM('1','0'),
	C_RETORNO ENUM('1','0') 
);
			/*Comandos (INSERT'S, UPDATE'S E DELETE'S)*/
insert into PACIENTE (IDPACIENTE) values (1);

update PACIENTE set CLASSIFICACAO = "vermelho" where IDPACIENTE = 1;

update PACIENTE set C_EXAME = "1" where IDPACIENTE = 1;

update PACIENTE set C_MEDICAMENTO = "1" where IDPACIENTE = 1;

update PACIENTE set C_RETORNO = "0" where IDPACIENTE = 1;

DELETE FROM PACIENTE;


			/*Comandos p/ projeções*/
select CLASSIFICACAO as "classificacao?" from PACIENTE where IDPACIENTE = 1;
select C_EXAME as "exame?" from PACIENTE where IDPACIENTE = 1;
select C_MEDICAMENTO as "medicamento?" from PACIENTE where IDPACIENTE = 1;
select C_RETORNO as "retorno?" from PACIENTE where IDPACIENTE = 1;