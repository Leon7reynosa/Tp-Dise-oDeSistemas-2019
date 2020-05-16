  package persistenciaDatos.Repositiorios;

import persistenciaDatos.daos.DAO;

  public abstract class Repositorio{

	protected DAO dao;

	public void setDao(DAO _dao){
		this.dao = _dao;
	}

	public void agregar(Object unObjeto){
		this.dao.agregar(unObjeto);
	}

	public void eliminar(Object unObjeto){
		this.dao.eliminar(unObjeto);
	}

	public void modificar(Object unObjeto){
		this.dao.modificar(unObjeto);
	}
}
