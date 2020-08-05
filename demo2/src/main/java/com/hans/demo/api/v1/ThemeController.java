package com.hans.demo.api.v1;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.hans.demo.exception.http.NotFoundException;
import com.hans.demo.model.Theme;
import com.hans.demo.service.ThemeService;
import com.hans.demo.vo.ThemePureVO;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/theme")
public class ThemeController {
    @Autowired
    private ThemeService themeService;

    @GetMapping("/by/names")
    public List<ThemePureVO> getThemeGroupByNames(String names){
        // 解析url提交过来的names
        List<String> nameList = Arrays.asList(names.split(","));
        // 查询所有的和names相关的主题
        List<Theme> themes = themeService.getByNames(nameList);
        // 存放简化后的vo对象的数组
        List<ThemePureVO> list = new ArrayList<>();

        // 将themes中的信息简化一下成ThemePureVO对象
        themes.forEach(theme->{
            Mapper mapper = DozerBeanMapperBuilder.buildDefault();
            ThemePureVO vo = mapper.map(theme, ThemePureVO.class);
            list.add(vo);
        });

        return list;
    }

    @GetMapping("/name/{name}/with_spu")
    public Theme getThemeByNameWithSpu(@PathVariable(name = "name") String themeName){
        Optional<Theme> optionalTheme = this.themeService.getByName(themeName);
        return optionalTheme.orElseThrow(()-> new NotFoundException(30003));
    }
}
