START TRANSACTION;
	INSERT unisocial.tela(id, nombre)
	VALUES(1, 'Algodon'),
		  (2,'Seda'),
          (3,'Poliester'),
          (4,'Lycra'),
          (5,'Cuero'),
          (6,'Nylon'),
          (7,'Jean')
    ;
    INSERT unisocial.tipos_de_prenda(id,categoria,nombre,abrigo,imagen)
    VALUES(1,'SUPERIOR','Remera cuello redondo manga corta', 5,'/img/remera_sugerencia.png'),
		  (2,'SUPERIOR','Remera cuello redondo manga larga', 8,'/img/remera_manga_larga_sugerencia.png'),
          (3,'SUPERIOR','Remera escote V manga corta', 5,'/img/remera_sugerencia.png'),
          (4,'SUPERIOR','Remera escote V manga larga', 8,'/img/remera_manga_larga_sugerencia.png'),
          (5,'SUPERIOR','Sueter', 15,'/img/sueter_sugerencia.png'),
          (6,'SUPERIOR','Campera', 13,'/img/campera_sugerencia.png'),
          (7,'INFERIOR','Pantalon largo', 8,'/img/pantalon_sugerencia.png'),
          (8,'INFERIOR','Pantalon corto', 3,'/img/bermuda_sugerencia.png'),
          (9,'INFERIOR','Bermuda', 3,'/img/bermuda_sugerencia.png'),
          (10,'INFERIOR','Pollera', 3,'/img/pollera_sugerencia.png'),
          (11,'INFERIOR','Calza', 5,'/img/pantalon.png'),
          (12,'SUPERIOR','Buzo', 13,'/img/buzo_sugerencia.png'),
          (13,'SUPERIOR','Musculosa', 3,'/img/musculosa_sugerencia.png'),
          (14,'CALZADO','Zapatillas', 0,'/img/zapatillas_sugerencia.png'),
          (15,'CALZADO','Zapatos', 0,'/img/zapatillas_sugerencia.png'),
          (16,'CALZADO','Sandalias', 0,'/img/zapatillas_sugerencia.png')
          ;
    INSERT unisocial.tela_permitida(tipo_prenda_id,tela_id)
    VALUES(1,1),(1,2),(1,3),(1,4),
		  (2,1),(2,2),(2,3),(2,4),
          (3,1),(3,2),(3,3),(3,4),
          (4,1),(4,2),(4,3),(4,4),
          (5,1),(5,2),(5,3),
          (6,1),(6,2),(6,3),(6,5),(6,6),
          (7,1),(7,2),(7,3),(7,6),(7,7),
          (8,1),(8,2),(8,3),(8,6),(8,7),
          (9,1),(9,2),(9,3),(9,6),(9,7),
          (10,1),(10,2),(10,3),(10,6),(10,7),
          (11,1),(11,4),(11,3),(11,6),
          (12,1),(12,4),(12,3),(12,6),
          (13,1),(12,4),
          (14,5),(14,6),
          (15,5),
          (16,5)
    ;
    INSERT unisocial.tipos_combinables(prendaBase_id,prendaCombinable_id)
    VALUES(1,5),(1,6),(1,7),(1,8),(1,10),(1,9),(1,11),(1,12),
		  (2,5),(2,6),(2,7),(2,8),(2,10),(2,9),(2,11),(2,12),
          (3,5),(3,6),(3,7),(3,8),(3,10),(3,9),(3,11),(3,12),
          (4,5),(4,6),(4,7),(4,8),(4,10),(4,9),(4,11),(4,12),
          (5,1),(5,2),(5,3),(5,4),(5,6),(5,7),
          (6,1),(6,2),(6,3),(6,4),(6,6),(6,7),(6,8),(6,9),(6,10),(6,11),
		  (7,1),(7,2),(7,3),(7,4),(7,5),(7,6),(7,13),
          (8,1),(8,2),(8,3),(8,4),(8,6),(8,13),
          (9,1),(9,2),(9,3),(9,4),(9,6),(9,13),
          (10,1),(10,2),(10,3),(10,4),(10,6),(10,13),
          (11,1),(11,2),(11,3),(11,4),(11,6),(11,13),
          (12,1),(12,2),(12,3),(12,4),(12,13),(12,7),(12,8),(12,9),(12,10),
          (13,12),(13,6),(13,8),(13,11),(13,10),
          (14,1),(14,2),(14,3),(14,4),(14,5),(14,6),(14,7),(14,8),(14,9),(14,10),(14,11),(14,12),(14,13),
          (15,7),(15,10),
          (16,7),(16,8),(16,10)
    ;
	
COMMIT;