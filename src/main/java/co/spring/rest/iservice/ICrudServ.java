package co.spring.rest.iservice;

import java.util.Map;

public interface ICrudServ <E>{

    public static final String CONTENT = "content";

    public static final String NUMBER_PAGE = "numberPage";

    public static final String SIZE_PAGE = "sizePage";

    public static final String NUMBER_OF_ELEMENTS = "numberOfElements";

    public static final String TOTAL_PAGE = "totalPage";

    public static final String TOTAL_ELEMENTS = "totalElements";

    public static final String IS_FIRST = "isFirst";

    public static final String IS_LAST = "isLast";

    public static final String IS_EMPTY = "isEmpty";

    Map<String,Object> getList(Integer pageNumber, Integer pageSize, String sort,String direction);

    E findById(long id);

    E add(E e);

    E update(long id, E e);

    E delete(long id);

}
