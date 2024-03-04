package br.com.azship.gestaofretesapi.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static br.com.azship.gestaofretesapi.modulos.comum.util.Constants.DEZ;
import static br.com.azship.gestaofretesapi.modulos.comum.util.Constants.ZERO;

@Data
@AllArgsConstructor
public class PageRequest implements Pageable{

    private int page;
    private int size;
    private String orderBy;
    private String orderDirection;

    public PageRequest() {
        this.page = ZERO;
        this.size = DEZ;
        this.orderBy = "id";
        this.orderDirection = "DESC";
    }

    @Override
    public long getOffset() {
        return (long) page * size;
    }

    @Override
    public int getPageNumber() {
        return page;
    }

    @Override
    public int getPageSize() {
        return size;
    }

    @Override
    public Sort getSort() {
        return orderBy == null ? Sort.unsorted() :
                Sort.by(Sort.Direction.fromString(this.orderDirection), this.orderBy);
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}
