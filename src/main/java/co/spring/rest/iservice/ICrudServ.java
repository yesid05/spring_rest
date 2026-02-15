package co.spring.rest.iservice;

import java.util.List;

public interface ICrudServ <E>{

    List<E> getList();

    E findById(long id);

    E add(E e);

    E update(long id, E e);

    E delete(long id);

}
