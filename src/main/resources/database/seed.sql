INSERT INTO role (nome) VALUES ('SUPERVISOR');
INSERT INTO role (nome) VALUES ('ADMINISTRADOR');

INSERT INTO usuario (nome,cpf,email,senha) VALUES ('Supervisor','31454032065','supervisor@supervisor.com','09348c20a019be0318387c08df7a783d');
INSERT INTO usuario (nome,cpf,email,senha) VALUES ('Supervisor2','42295934060','supervisor2@supervisor.com','09348c20a019be0318387c08df7a783d');
INSERT INTO usuario (nome,cpf,email,senha) VALUES ('Supervisor3','21984479083','supervisor3@supervisor.com','09348c20a019be0318387c08df7a783d');
INSERT INTO usuario (nome,cpf,email,senha) VALUES ('Admin','08552440032','admin@admin.com','21232f297a57a5a743894a0e4a801fc3');

INSERT INTO usuario_role (usuario_id,role_id) VALUES (1,1);
INSERT INTO usuario_role (usuario_id,role_id) VALUES (2,1);
INSERT INTO usuario_role (usuario_id,role_id) VALUES (3,1);
INSERT INTO usuario_role (usuario_id,role_id) VALUES (4,2);

INSERT INTO endereco (numero, rua, bairro, cidade, estado, pais) VALUES ('103B','Servidão João Paetzel','Ingleses', 'FLorianópolis','Santa Catarina','Brasil');
INSERT INTO endereco (numero, rua, bairro, cidade, estado, pais) VALUES ('1127','Rua José Martins Cabral','Humaitá','Tubarão','Santa Catarina','Brasil');
INSERT INTO endereco (numero, rua, bairro, cidade, estado, pais) VALUES ('570','Rua Moema','Estreito','Florianópolis','Santa Catarina','Brasil');

INSERT INTO empresa (cnpj,nome,regional_senai,segmento,endereco_id,usuario_id) VALUES ('90946134000192', 'EmpresaUm', 'LITORAL_SUL', 'INDUSTRIA_GRAFICA',1,1);
INSERT INTO empresa (cnpj,nome,regional_senai,segmento,endereco_id,usuario_id) VALUES ('31270455000150','EmpresaDois','SUDESTE','CONSTRUCAO',2,2);
INSERT INTO empresa (cnpj, nome, regional_senai, segmento, endereco_id, usuario_id) VALUES ('28829595000101', 'EmpresaTres', 'VALE_DO_ITAJAI_MIRIM', 'INDUSTRIA_DIVERSA', 3, 3);

UPDATE usuario SET empresa_id = 1 WHERE id = 1;
UPDATE usuario SET empresa_id = 2 WHERE id = 2;
UPDATE usuario SET empresa_id = 3 WHERE id = 3;

UPDATE endereco SET empresa_id = 1 WHERE id = 1;
UPDATE endereco SET empresa_id = 2 WHERE id = 2;
UPDATE endereco SET empresa_id = 3 WHERE id = 3;

INSERT INTO curso (nome,apelido,ocupacao,descricao,empresa_id) VALUES ('Dev FullstackEmpresaUm12022','Dev Fullstack1','Dev Fullstack','Primeiro curso para formação de desenvolvedores fullstack',1);
INSERT INTO curso (nome,apelido,ocupacao,descricao,empresa_id) VALUES ('Desenvolvedor WebEmpresaUm12022','Desenvolvedor Web1','Desenvolvedor Web','Criação de páginas web responsivas',1);
INSERT INTO curso (nome,apelido,ocupacao,descricao,empresa_id) VALUES ('Analista de SistemasEmpresaUm12022','Analista de Sistemas1','Analista de Sistemas','Curso para análise e levantamento de requisitos de projetos',1);
INSERT INTO curso (nome,apelido,ocupacao,descricao,empresa_id) VALUES ('QAEmpresaDois12022','QA1','QA','Curso de auditoria de funcionalidades de projetos',2);
INSERT INTO curso (nome,apelido,ocupacao,descricao,empresa_id) VALUES ('DBAEmpresaDois12022','DBA1','DBA','Preparatório para elaboração, segurança e melhora na performance do banco de dados',2);
INSERT INTO curso (nome,apelido,ocupacao,descricao,empresa_id) VALUES ('Dev FullstackEmpresaDois12022','Dev Fullstack1','Dev Fullstack','Preparação para formação de desenvolvedores fullstack',2);
INSERT INTO curso (nome,apelido,ocupacao,descricao,empresa_id) VALUES ('Desenvolvedor Mobile AndriodEmpresaTres12022','Desenvolvedor Mobile Android1','Desenvolvedor Mobile Android','Curso destinado na formação de desenvolvedores mobile na linguagem Flutter para dispositivos android',3);
INSERT INTO curso (nome,apelido,ocupacao,descricao,empresa_id) VALUES ('Desenvolvedor Backend JavaEmpresaTres12022','Desenvolvedor Backend Java1','Desenvolvedor Backend Java','Preparatório para formação de programadores que desenvolvam features no backend das aplicações',3);
INSERT INTO curso (nome,apelido,ocupacao,descricao,empresa_id) VALUES ('Desenvolvedor IOSEmpresaTres12022','Desenvolvedor IOS1','Desenvolvedor IOS','Curso para formação de desenvolvedorees de aplicações IOS com a linguagem Swift',3);

INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('Algoritmos',now(),(now() + INTERVAL '20 DAYS'),1);
INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('Front-end',(now() + INTERVAL '20 DAYS'), (now() + INTERVAL '110 DAYS'),1);
INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('Back-end',(now() + INTERVAL '110 DAYS'), (now() + INTERVAL '220 DAYS'),1);

INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('HTML e CSS',(now() - INTERVAL '10 DAYS'),(now() + INTERVAL '50 DAYS'),2);
INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('React',(now() + INTERVAL '50 DAYS'), (now() + INTERVAL '140 DAYS'),2);
INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('Soft Skills',(now() + INTERVAL '140 DAYS'),(now() + INTERVAL '160 DAYS'),2);

INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('Metodologias Ágeis',now(),(now() + INTERVAL '30 DAYS'),3);
INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('UML e SQL',(now() + INTERVAL '30 DAYS'),(now() + INTERVAL '60 DAYS'),3);
INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('Lógica de Programação e Algoritmos',(now() + INTERVAL '60 DAYS'),(now() + INTERVAL '150 DAYS'),3);

INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('Algoritmos II',(now() - INTERVAL '30 DAYS'),now(), 4);
INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('Testes unitários',now(),(now() + INTERVAL '30 DAYS'),4);
INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('Soft Skills',(now() + INTERVAL '30 DAYS '),(now() + INTERVAL '45 DAYS'),4);

INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('UML',(now() - INTERVAL '30 DAYS'),(now() - INTERVAL '15 DAYS'),5);
INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('SQL',(now() - INTERVAL '15 DAYS'),(now() + INTERVAL '45 DAYS'),5);
INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('No SQL', (now() + INTERVAL '45 DAYS'), (now() + INTERVAL '75 DAYS'),5);

INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('HTML, CSS e Sass', now(),(now() + INTERVAL '90 DAYS'),6);
INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('Javascript e React', (now() + INTERVAL '90 DAYS'), (now() + INTERVAL '180 DAYS'),6);
INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('MongoDB', (now() + INTERVAL '180 DAYS'),(now() + INTERVAL '240 DAYS'), 6);

INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('Android Studio + Algoritmos',(now() - INTERVAL '15 DAYS'), now(),7);
INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('UI + Flutter',now(), (now() + INTERVAL '90 DAYS'),7);
INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('API REST + Fundamentos de Cloud', (now() + INTERVAL '90 DAYS'),(now() + INTERVAL '180 DAYS'),7);

INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('Algoritmos III',(now() - INTERVAL '180 DAYS'),(now() - INTERVAL '150 DAYS'),8);
INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('Java Intermediário',(now() - INTERVAL '150 DAYS'),(now() - INTERVAL '60 DAYS'),8);
INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('JPA/Hibernate',(now() - INTERVAL '60 DAYS'), now(),8);

INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('IDE Xcode Tutorial',(now() - INTERVAL '15 DAYS'),now(),9);
INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('Fundamentos de Swift',now(),(now() + INTERVAL '90 DAYS'),9);
INSERT INTO modulo(nome,data_inicio,data_termino,curso_id) VALUES ('UI/UX',(now() + INTERVAL '90 DAYS'),(now() + INTERVAL '150 DAYS'),9);

INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Carlos da Silva','80133541053','Desenvolvimento','Desenvolvimento Frontend',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Élton Oliveira Quinterno','89880422040','Desenvolvimento','Desenvolvimento Frontend',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Edna Quaresma Lucas','30177325097','Desenvolvimento','Desenvolvimento Frontend',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Janaína Carvalho Valadares','38543639077','Desenvolvimento','Desenvolvimento Backend',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Rayan Parente Jesus','10301344051','Desenvolvimento','Desenvolvimento Backend',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Zilda Custódio Varão','77422043040','Desenvolvimento','Desenvolvimento Backend',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Lyana Norões Domingues','00829520007','Desenvolvimento','Desenvolvimento Banco de Dados',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Noah Coelho Carreira','32988890080','Desenvolvimento','Desenvolvimento Banco de Dados',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Stéphanie Amaral Pessoa','80691178003','Desenvolvimento','Desenvolvimento Banco de Dados',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Deivid Conceição Varela','30205983030','Desenvolvimento','Desenvolvimento Banco de Dados',1);

INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Karen Cisneiros Vieira','36407771072','Desenvolvimento Web','Desenvolvimento Frontend Javascript',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Élia Campelo Uchoa','06326008026','Desenvolvimento Web','Desenvolvimento Frontend Javascript',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Giorgi Castanho Mateus','01356917020','Desenvolvimento Web','Desenvolvimento Frontend Javascript',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Nélia Silvestre Nóbrega','49006585025','Desenvolvimento Web','Desenvolvimento Frontend PHP',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Eurico Gusmão Marques','82511844010','Desenvolvimento Web','Desenvolvimento Frontend PHP',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Emily Bensaúde Areosa','08778783070','Desenvolvimento Web','Desenvolvimento Frontend PHP',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Duarte Coronel Caiado','77898289080','Desenvolvimento Web','Desenvolvimento React',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Ionut Milheiro Cambezes','02726136060','Desenvolvimento Web','Desenvolvimento React',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Anael Bouça Pinhal','81516634055','Desenvolvimento Web','Desenvolvimento Python',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Ângelo Cedraz Baptista','18762174053','Desenvolvimento Web','Desenvolvimento Python',1);

INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Ary Sobreira Guerreiro','79355646046','Planejamento','PO',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Sol Meneses Lousã','81653657030','Planejamento','Modelagem',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Pérola Valentim Pontes','34924481009','Planejamento','Modelagem',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Valter da Costa','57943110021','Planejamento','Modelagem',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Anaisa Luz Cavaco','87193762001','Planejamento','Agilista',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Josué Albernaz Lousada','25343224008','Planejamento','Arquiteto de Cloud',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Yasmine Milhães Jardim','35534986093','Planejamento','Arquiteto de Cloud',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Lea Sintra Miranda','54163551077','Planejamento','Arquiteto de Cloud',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Willian Curvelo','40042216095','Planejamento','PO',1);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Lázaro Quintão da Silva','99942739068','Planejamento','PO',1);

INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Gabriela Anes Barbosa','66658755043','Auditoria','Analista de Código Java',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Denzel Moura Faria','72876221063','Auditoria','Analista de Código Java',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Ayla Ourique Castilhos','15989616082','Auditoria','Analista de Código Java',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Melinda Picanço Pescada','37511105009','Auditoria','Analista de Código Javascript',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Atanas Durães Fortes','78053423000','Auditoria','Analista de Código Javascript',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Elsa Nobre Santana','67878230021','Auditoria','Analista de Código PHP',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Ian Bastos Rosa','85825344004','Auditoria','Analista de Código PHP',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Hellen Urias Amaro','55691580060','Auditoria','Analista de Código Python',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Janaína Toledo Anjos','48625383041','Auditoria','Analista de BD',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Victória Sabala Barreiros','95644303055','Auditoria','Analista de BD',2);

INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Nélio Caeira Caetano','00230701027','Banco de dados','UML',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Eder Flávio Atilano','07481110000','Banco de dados','UML',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Léon Abasto Murtinho','56011886027','Banco de dados','UML',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Joshua Rangel Soares','60732769078','Banco de dados','Suporte SQL',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Dante Moita Cunha','28441464065','Banco de dados','Suporte SQL',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Célio Alcoforado Quintela','89528575005','Banco de dados','Suporte SQL',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Ion Canhão Ornelas','88793107048','Banco de dados','Suporte SQL',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Shayan Carvalhosa Abrantes','43121656023','Banco de dados','Suporte SQL',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Rubi Sarmento Gois','98795364013','Banco de dados','Suporte NoSQL',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Salvador Penha Grangeia','79465331015','Banco de dados','Suporte NoSQL',2);

INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Emilia Ruela Cerqueira','12782581082','Desenvolvimento','Desenvolvimento Java',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Ariela Bragança Caniça','30570621054','Desenvolvimento','Desenvolvimento Java',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Eduardo Toledo Alpuim','35124947063','Desenvolvimento','Desenvolvimento Javascript',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Vitor Paixão Campelo','01043013091','Desenvolvimento','Desenvolvimento Javascript',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Gabriel Larroque','77212229075','Desenvolvimento','Desenvolvimento Javascript',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Olga Bouça Parreir','38683322009','Desenvolvimento','Desenvolvimento Python',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Eliane Castro Miguéis','70009595090','Desenvolvimento','Desenvolvimento PHP',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Leandro Quintana Madeira','33714315098','Desenvolvimento','Desenvolvimento PHP',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('João Gomes','71316133044','Desenvolvimento','Desenvolvimento COBOL',2);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Omar Moita Ferreira','10914921070','Desenvolvimento','Desenvolvimento Malbolge',2);

INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Mauri Canhão Eiró','73598625073','Desenvolvimento Mobile','Desenvolvimento Flutter',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Lucy Beltrão Cabral','89788990053','Desenvolvimento Mobile','Desenvolvimento Flutter',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Elisete Serro Escobar','88674762034','Desenvolvimento Mobile','Desenvolvimento Flutter',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Jonata Gago Faustino','42891564014','Desenvolvimento Mobile','Desenvolvimento UI/UX',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Mel Vilaverde','62173870009','Desenvolvimento Mobile','Desenvolvimento UI/UX',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Teotónio Bonilha Silva','57110230040','Desenvolvimento Mobile','Desenvolvimento UI/UX',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Jade Mendes Luz','74030412084','Desenvolvimento Mobile','Tech-Lead',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Lais Vilanova Barbalho','00608932060','Desenvolvimento Mobile','Desenvolvimento integração BD',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Flávio Henriques Rico','08228375018','Desenvolvimento Mobile','Desenvolvimento integração Backend',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Liane Ruela Dantas','80898718015','Desenvolvimento Mobile','Desenvolvimento integração Backend',3);

INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Telma Canadas Tinoco','19824877045','TI','Tech-Lead',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Célio Vergueiro Castilhos','92130467075','TI','Desenvolvimento Java Jr',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Lorena Palhares Gama','22725004047','TI','Desenvolvimento Java Jr',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Patrícia Martins Ataíde','15698647054','TI','Desenvolvimento Java Jr',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Milana Padilha Candal','89384924008','TI','Desenvolvimento Java Jr',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Doriana Belém Filipe','20696272091','TI','Desenvolvimento Java Pleno',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Heloísa Torrado Bouças','59909241005','TI','Desenvolvimento Java Pleno',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('César Pereira Ribas','89837408090','TI','Desenvolvimento Java Pleno',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Rafael César Martins','84760519050','TI','Desenvolvimento Java Sênior',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Jaime Onofre Prada','34983731024','TI','Desenvolvimento Java Sênior',3);

INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Gael Pires Antunes','55748853019','Desenvolvimento Mobile','Desenvolvimento Swift',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Graça Abrantes Junqueira','68349830077','Desenvolvimento Mobile','Desenvolvimento Swift',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Maira Bentes Bingre','41899266046','Desenvolvimento Mobile','Desenvolvimento Swift',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Alissa Linhares Viveiros','60057121010','Desenvolvimento Mobile','Desenvolvimento Swift',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Angela Gameiro Calçada','31496899024','Desenvolvimento Mobile','Desenvolvimento UX/UI IOS',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Zayn Gusmão Talhão','98349058072','Desenvolvimento Mobile','Desenvolvimento UX/UI IOS',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Noé Torrado Guerra','09448809085','Desenvolvimento Mobile','Desenvolvimento UX/UI IOS',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Lian Prado Lage','90939515059','Desenvolvimento Mobile','Desenvolvimento Backend',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Lucía Fontoura','40148702007','Desenvolvimento Mobile','Desenvolvimento Backend',3);
INSERT INTO trabalhador (nome, cpf,  setor, funcao, empresa_id) VALUES ('Anastácia Anjos Franca','75426185042','Desenvolvimento Mobile','Desenvolvimento BD',3);

INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (1, 1);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (2, 1);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (3, 1);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (4, 1);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (5, 1);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (6, 1);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (7, 1);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (8, 1);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (9, 1);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (10, 1);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (11,2);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (12,2);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (13,2);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (14,2);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (15,2);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (16,2);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (17,2);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (18,2);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (19,2);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (20,2);

INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (21,3);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (22,3);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (23,3);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (24,3);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (25,3);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (26,3);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (27,3);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (28,3);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (29,3);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (30,3);

INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (31,4);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (32,4);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (33,4);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (34,4);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (35,4);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (36,4);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (37,4);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (38,4);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (39,4);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (40,4);

INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (41,5);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (42,5);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (43,5);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (44,5);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (45,5);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (46,5);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (47,5);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (48,5);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (49,5);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (50,5);

INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (51,6);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (52,6);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (53,6);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (54,6);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (55,6);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (56,6);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (57,6);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (58,6);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (59,6);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (60,6);

INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (61,7);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (62,7);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (63,7);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (64,7);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (65,7);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (66,7);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (67,7);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (68,7);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (69,7);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (70,7);

INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (71,8);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (72,8);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (73,8);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (74,8);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (75,8);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (76,8);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (77,8);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (78,8);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (79,8);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (80,8);

INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (81,9);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (82,9);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (83,9);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (84,9);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (85,9);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (86,9);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (87,9);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (88,9);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (89,9);
INSERT INTO trabalhador_curso (trabalhador_id, curso_id) VALUES (90,9);

INSERT INTO habilidade (nome,modulo_id) VALUES ('Entendimento da lógica de programação e estruturas de dados',1);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Desenvolvimeto de HTML, CSS e Javascript para criar páginas responsivas',2);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Entendimento e integração da interface com features implementadas no brackend',3);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Elaboração de páginas Web responsivas e estilizadas',4);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Componentização de elementos e fundamento de React',5);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Entendimento de boas práticas comportamentais no desenvolvimento de software',6);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Conhecimento em SCRUM+Kanban e TDD',7);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Modelagem e conhecimento em D*L do SGBD relacional',8);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Conhecimento sobre estruturas de dados e algoritmos padrões para resolução de problemas habituais',9);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Conhecimento aprofundado e intermediário sobre estrutura de dados e sua complexidade',10);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Eçaboração de testes em sistemas de informação',11);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Entendimento de boas práticas comportamentais no desenvolvimento de software',12);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Modelagem de Banco de dados relacionais',13);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Conhecimento na elaboração de queries de banco de dados relacionais',14);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Conehcimento e implementação de banco NoSQL',15);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Elaboração de páginas responsivas e estilizadas com pré-processadores CSS',16);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Conhecimneto sobre fundamentos de Javascript e componentização com React',17);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Criação de aplicações CRUD com branco de dados não relacional',18);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Apresentação da plataforma de desenvolvimento e abordagemm aos fundamentos das estruturas de dados',19);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Noção de Interface do Usuário e fundamentos de FLutter',20);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Gerenciamento de requisições e utilização de recursos em nuvem',21);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Conhecimento sólido em estrutura de dados e complexidade de algoritmos',22);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Proficiência intermediária em Java',23);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Criação de requisições, tratamento e listagem de dados com Hibernate e especificação para persistência de dados JPA',24);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Conhecimento do ambiente de desenvolvimento da linguagem swift',25);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Proficiência sólida em Swift',26);
INSERT INTO habilidade (nome,modulo_id) VALUES ('Noção sobre Interface do Usuário e Experiência do Usuário na elaboração de aplicações',27);

INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',1);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',2);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',3);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',4);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',5);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',6);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',7);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',8);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',9);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',10);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',11);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',12);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',13);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',14);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',15);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',16);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',17);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',18);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',19);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',20);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',21);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',22);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',23);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',24);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',25);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',26);
INSERT INTO avaliacao (status, modulo_id) VALUES ('EM_ESPERA',27);

INSERT INTO nota (nota, avaliacao_id, trabalhador_id) VALUES (4.0, 1, 1);
INSERT INTO nota (nota, avaliacao_id, trabalhador_id) VALUES (5.8, 1, 2);
INSERT INTO nota (nota, avaliacao_id, trabalhador_id) VALUES (6.0, 1, 3);
INSERT INTO nota (nota, avaliacao_id, trabalhador_id) VALUES (7.0, 1, 4);
INSERT INTO nota (nota, avaliacao_id, trabalhador_id) VALUES (5.0, 1, 5);
INSERT INTO nota (nota, avaliacao_id, trabalhador_id) VALUES (8.5, 1, 6);
INSERT INTO nota (nota, avaliacao_id, trabalhador_id) VALUES (7.3, 1, 7);
INSERT INTO nota (nota, avaliacao_id, trabalhador_id) VALUES (6.7, 1, 8);
INSERT INTO nota (nota, avaliacao_id, trabalhador_id) VALUES (8.4, 1, 9);
INSERT INTO nota (nota, avaliacao_id, trabalhador_id) VALUES (9.2, 1, 10);

UPDATE avaliacao SET status = 'CONCLUIDA' WHERE id = 1;

INSERT INTO nota (nota, avaliacao_id, trabalhador_id) VALUES (9.0, 2, 1);
INSERT INTO nota (nota, avaliacao_id, trabalhador_id) VALUES (8.0, 2, 2);
INSERT INTO nota (nota, avaliacao_id, trabalhador_id) VALUES (7.0, 2, 3);
INSERT INTO nota (nota, avaliacao_id, trabalhador_id) VALUES (6.0, 2, 4);
INSERT INTO nota (nota, avaliacao_id, trabalhador_id) VALUES (5.0, 2, 5);
INSERT INTO nota (nota, avaliacao_id, trabalhador_id) VALUES (4.0, 2, 6);
INSERT INTO nota (nota, avaliacao_id, trabalhador_id) VALUES (3.0, 2, 7);
INSERT INTO nota (nota, avaliacao_id, trabalhador_id) VALUES (2.0, 2, 8);
INSERT INTO nota (nota, avaliacao_id, trabalhador_id) VALUES (1.0, 2, 9);
INSERT INTO nota (nota, avaliacao_id, trabalhador_id) VALUES (0.0, 2, 10);

UPDATE avaliacao SET status = 'EM_AVALIACAO' WHERE id = 2;
