package cn.zhh.admin.rsp;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 自定义分页数据
 *
 * @author z_hh
 * @date 2019/7/6
 */
@Data
public class Page<T> implements Serializable {
    private static final long serialVersionUID = -4826887084084123374L;

    private int size;

    private int current;

    private List<T> records = Collections.emptyList();

    private long pages;

    private long total;

    public static <T> Page<T> parse(org.springframework.data.domain.Page<T> originalPage) {
        Page<T> page = new Page<>();
        page.setSize(originalPage.getSize());
        page.setCurrent(originalPage.getNumber() + 1);
        page.setRecords(originalPage.getContent());
        page.setPages(originalPage.getTotalPages());
        page.setTotal(originalPage.getTotalElements());
        return page;
    }

    public <E> Page<E> recoreConvert(Function<T, E> function) {
        // 将原分页除了records的其它属性拷贝到新分页
        List<T> originalRecords = this.getRecords();
        this.setRecords(null);
        Page<E> newPage = new Page<>();
        BeanUtils.copyProperties(this, newPage);
        // recores转换
        List<E> newRecored = originalRecords.stream().map(function).collect(Collectors.toList());
        newPage.setRecords(newRecored);
        // 返回新的分页
        return newPage;
    }
}
