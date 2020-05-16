START TRANSACTION;
	
	/*le cambiamos el estado a jazul, la cual la creamos con la abm, tambien a alejandro*/
    UPDATE unisocial.estado
    SET tipo = 'premium'
    WHERE id = 2;
    
    /*guardarropas*/
    INSERT INTO unisocial.guardarropa(id,descripcion,estilo_vestimenta,fecha_ingreso,nombre)
    VALUES(1,'Guardarropa de uso diario','CASUAL',null,'Guardarropa Casual'),
		  (2,'Guardarropa de uso diario','CASUAL',null,'Guardarropa Casual')
    ;
    /*asigno guardarropa*/
    INSERT INTO unisocial.usuario_guardarropa(usuario_id,guardarropa_id)
    VALUES(1,1),
		  (2,2)
    ;
    /*colores*/
    INSERT INTO unisocial.color(id,colorHexa)
    VALUES(1,'#000000'),/*negro*/
		  (2,'#ffffff'),/*blanco*/
          (3,'#ffff00'),/*amarillo*/
          (4,'#00aae4'),/*celeste*/
          (5,'#828282'),/*Gris*/
          (6,'#641c34'),/*bordo*/
          (7,'#008f39')/*verde*/
    ;
    /*prendas*/
    INSERT INTO unisocial.prenda(id,imagen,en_uso,material_id,tela_id,tipoPrenda_id,guardarropa_id)
    VALUES(1,'/Imagenes/remera_manga_corta_redondo.jpg',0,null,1,1,1),
		  (2,'/Imagenes/remera_cuello_v_corta.jpg',0,null,4,3,1),
          (3,'/Imagenes/musculosa.jpg',0,null,4,13,1),
          (4,'/Imagenes/campera_cuero.jpg',0,null,5,6,1),
          (5,'/Imagenes/sueter.jpg',0,null,3,5,1),
          (6,'/Imagenes/pantalon_corto.jpg',0,null,7,9,1),
          (7,'/Imagenes/pantalon_largo.jpg',0,null,6,7,1),
          (8,'/Imagenes/zapatillas_bordo.jpg',0,null,6,14,1),
          (9,'/Imagenes/zapatos_cuero.jpg',0,null,5,15,1),
          (10,'/Imagenes/remera_cuello_redondo_larga.jpg',0,null,4,2,2),
          (11,'/Imagenes/remera_cuello_v_larga.jpg',0,null,1,4,2),
          (12,'/Imagenes/musculosa_mujer.jpg',0,null,4,13,2),
          (13,'/Imagenes/sueter.jpg',0,null,3,5,2),
          (14,'/Imagenes/pollera.jpg',0,null,2,10,2),
          (15,'/Imagenes/calza.jpg',0,null,6,11,2),
          (16,'/Imagenes/buzo_mujer.jpg',0,null,1,12,2),
          (17,'/Imagenes/zapatos_mujer.jpg',0,null,5,15,2),
          (18,'/Imagenes/sandalias.jpg',0,null,5,16,2)
	;  
    
    INSERT INTO unisocial.prenda_color(prenda_id,color_id)
    VALUES(1,1),(2,2),(3,3),(4,2),(5,2),(6,4),(7,5),(8,6),(9,1),
          (10,3),(11,2),(12,7),(13,5),(14,1),(15,1),(16,2),(17,1),(18,1)
	;
    
    INSERT INTO unisocial.molde(id)
    VALUES(1),(2),(3),(4), /*moldes con base = remera cuello redondo manga corta*/
		  (5),(6),(7),(8), /*moldes con base = remera cuello redondo manga larga*/
          (9),(10),(11),(12),/*moldes con base = remera escote v manga corta*/
          (13),(14),(15),(16),/*moldes con base = remera escote v manga larga*/
          /*hasta ahi solo remeras como superior, ahora superponibles*/
          (17),(18),(19),(20), /*remeras con sueter arriba*/
          (21),(22),(23),(24), /*remeras con sueter con campera*/
          /*remeras con campera*/
          (25),(26),(27),(28),
          (29),(30),(31),(32),
          (33),(34),(35),(36),
          (37),(38),(39),(40),
          /*remeras con buzo*/
          (41),(42),(43),(44),
          (45),(46),(47),(48),
          (49),(50),(51),(52),
          (53),(54),(55),(56),
          /*muscolosa como base y sus superponibles*/
          (57),(58),(59),(60),
          /*ahora meto los zapatos*/
          (61),(62),(63),(64),
          (65),(66),(67),(68),
          (69),(70),(71),(72),
          (73),(74),(75),(76),
          (77),(78),(79),(80),
          (81),(82),(83),(84),
          (85),(86)
    ;
    
    /*el primero es la prenda base superior, despues lo demas esta en comentarios*/
    INSERT INTO unisocial.molde_tipoprenda(molde_id,tipoPrenda_id)
    VALUES(1,1),(1,7),(1,14), /*pantalon largo, zapatillas*/
          (2,1),(2,8),(2,14), /*pantalon corto, zapatillas*/
          (3,1),(3,9),(3,14), /*bermuda, zapatillas*/
          (4,1),(4,10),(4,14), /*pollera, zapatillas*/
          (5,2),(5,7),(5,14), /*lo mismo pero con manga larga*/
          (6,2),(6,8),(6,14), 
          (7,2),(7,9),(7,14), 
          (8,2),(8,10),(8,14),
          (9,3),(9,7),(9,14), /*lo mismo pero remera escote V manga corta*/
          (10,3),(10,8),(10,14),
          (11,3),(11,9),(11,14), 
          (12,3),(12,10),(12,14),
          (13,4),(13,7),(13,14), /*lo mismo pero remera escote V manga larga*/
          (14,4),(14,8),(14,14),
          (15,4),(15,9),(15,14), 
          (16,4),(16,10),(16,14),
          /*hasta aca remeras solas como base, ahora remeras con sueter*/
          (17,1),(17,5),(17,7),(17,14),
          (18,2),(18,5),(18,7),(18,14),
		  (19,3),(19,5),(19,7),(19,14),
          (20,4),(20,5),(20,7),(20,14),
          /*le superponemos a todas, una campera*/
          (21,1),(21,5),(21,6),(21,7),(21,14),
          (22,2),(22,5),(22,6),(22,7),(22,14),
          (23,3),(23,5),(23,6),(23,7),(23,14),
          (24,4),(24,5),(24,6),(24,7),(24,14),
          /*ahora remeras con camperas*/
          (25,1),(25,6),(25,7),(25,14),  /*cuello redondo, pantalon largo*/
          (26,1),(26,6),(26,8),(26,14),  /*pant cort*/
          (27,1),(27,6),(27,9),(27,14),  /*bermuda*/
          (28,1),(28,6),(28,10),(28,14), /*pollera*/
          (29,2),(29,6),(29,7),(29,14),  /*cuello redondo manga larga, lo mismo*/
          (30,2),(30,6),(30,8),(30,14),
          (31,2),(31,6),(31,9),(31,14), 
          (32,2),(32,6),(32,10),(32,14),
          (33,3),(33,6),(33,7),(33,14), /*escote V*/
          (34,3),(34,6),(34,8),(34,14),
          (35,3),(35,6),(35,9),(35,14), 
          (36,3),(36,6),(36,10),(36,14),
          (37,4),(37,6),(37,7),(37,14), /*escote V manga larga, lo mismo*/
          (38,4),(38,6),(38,8),(38,14),
          (39,4),(39,6),(39,9),(39,14), 
          (40,4),(40,6),(40,10),(40,14),
          /*ahora remeras con buzo*/
          (41,1),(41,12),(41,7),(41,14),  /*cuello redondo, pantalon largo*/
          (42,1),(42,12),(42,8),(42,14),  /*pant cort*/
          (43,1),(43,12),(43,9),(43,14),  /*bermuda*/
          (44,1),(44,12),(44,10),(44,14), /*pollera*/
          (45,2),(45,12),(45,7),(45,14),  /*cuello redondo manga larga, lo mismo*/
          (46,2),(46,12),(46,8),(46,14),
          (47,2),(47,12),(47,9),(47,14), 
          (48,2),(48,12),(48,10),(48,14),
          (49,3),(49,12),(49,7),(49,14), /*escote V*/
          (50,3),(50,12),(50,8),(50,14),
          (51,3),(51,12),(51,9),(51,14), 
          (52,3),(52,12),(52,10),(52,14),
          (53,4),(53,12),(53,7),(53,14), /*escote V manga larga, lo mismo*/
          (54,4),(54,12),(54,8),(54,14),
          (55,4),(55,12),(55,9),(55,14), 
          (56,4),(56,12),(56,10),(56,14),
          /*musculosa*/
          (57,13),(57,8),(57,14), /*pantalon corto*/
          (58,13),(58,10),(58,14), /*pollera*/
          (59,13),(59,6),(59,8),(59,14), /*campera*/
          (60,13),(60,12),(60,10),(60,14), /*buzo*/
          /*zapatos*/
          (61,1),(61,7),(61,15),/*con remeras*/
          (62,1),(62,10),(62,15),
          (61,2),(61,7),(61,15),
          (62,2),(62,10),(62,15),
          (61,3),(61,7),(61,15),
          (62,3),(62,10),(62,15),
          (61,4),(61,7),(61,15),
          (62,4),(62,10),(62,15),
          (63,1),(63,5),(63,7),(63,15),/*con sueter*/
          (64,2),(64,5),(64,7),(64,15),
		  (65,3),(65,5),(65,7),(65,15),
          (66,4),(66,5),(66,7),(66,15),
		  (67,1),(67,5),(67,6),(67,7),(67,15), /*sueter con campera*/
          (68,2),(68,5),(68,6),(68,7),(68,15),
          (69,3),(69,5),(69,6),(69,7),(69,15),
          (70,4),(70,5),(70,6),(70,7),(70,15),
          (71,1),(71,6),(71,7),(71,15), /*solo campera*/
          (72,1),(72,6),(72,10),(72,15),
          (73,2),(73,6),(73,7),(73,15), 
          (74,2),(74,6),(74,10),(74,15),
          (75,3),(75,6),(75,7),(75,15), 
          (76,3),(76,6),(76,10),(76,15),
          (77,4),(77,6),(77,7),(77,15), 
          (78,4),(78,6),(78,10),(78,15),
          (79,1),(79,12),(79,7),(79,15), /*con buzo*/
          (80,1),(80,12),(80,10),(80,15),
          (81,2),(81,12),(81,7),(81,15), 
          (82,2),(82,12),(82,10),(82,15),
          (83,3),(83,12),(83,7),(83,15), 
          (84,3),(84,12),(84,10),(84,15),
          (85,4),(85,12),(85,7),(85,15),
          (86,4),(86,12),(86,10),(86,15)
          
          
    ;
    
    
COMMIT;