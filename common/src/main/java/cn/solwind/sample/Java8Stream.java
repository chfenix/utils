package cn.solwind.sample;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.velocity.test.provider.Person;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Java8Stream {

    public static void main(String[] args) throws Exception {
        List<Java8StreamDTO> listDTO = init();

        /**
         * 提取DTO某一属性
         */
        System.out.println("提取DTO某一属性");
        System.out.println(listDTO.stream().map(e -> Long.valueOf(e.getId())).collect(Collectors.toList()));
        System.out.println();

        /**
         * 按照某一属性汇总DTO，Map-List
         */
        System.out.println("按照某一属性汇总DTO");
        Map<String, List<Java8StreamDTO>> listMap = listDTO.stream().collect(Collectors.groupingBy(Java8StreamDTO::getGroupKey));
        System.out.println(listMap);
        System.out.println();

        /**
         * 按照某一属性汇总合并DTO，返回合并后List，复合key group可以使用自定义方法获取复合key
         */
        System.out.println("按照某一属性汇总");
        List<Java8StreamDTO> listGroupDTO = listDTO.stream()
                .collect(Collectors.toMap(k -> groupKey(k), a -> a, (o1, o2) -> {
                    o1.setAmount(o1.getAmount().add(o2.getAmount()));
                    o1.setBeginDate(o1.getBeginDate().isAfter(o2.getBeginDate()) ? o2.getBeginDate() : o1.getBeginDate());
                    o1.setEndDate(o1.getEndDate().isBefore(o2.getEndDate()) ? o2.getEndDate() : o1.getEndDate());
                    o1.setId(o1.getId() + " - " + o2.getId());
                    return o1;
                })).values().stream().collect(Collectors.toList());
        System.out.println(listGroupDTO);
        System.out.println();
        listDTO = init();

        /**
         * 按照某一属性过滤
         */
        System.out.println("按照某一属性过滤");
        System.out.println(listDTO.stream().filter(t -> t.getBeginDate().isAfter(LocalDate.of(2021,4,15))).collect(Collectors.toList()));
        System.out.println();

        /**
         * 按照某一属性转Map，重复的覆盖
         */
        System.out.println("按照某一属性转Map，重复的覆盖");
        Map<String, Java8StreamDTO> listOneMap = listDTO.stream().collect(Collectors.toMap(Java8StreamDTO::getId, a -> a, (k1, k2) -> k1));
        System.out.println(listOneMap);
        System.out.println();

        /**
         * 某属性求和
         */
        System.out.println("某属性求和");
        BigDecimal totalRentArea = listDTO.stream().map(Java8StreamDTO::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(totalRentArea);
        System.out.println();

        /**
         * 分组求和
         */
        System.out.println("分组求和");
        Map<Optional<String>,BigDecimal> mapGroupSum = listDTO.stream().collect(
                        Collectors.groupingBy(
                                dto -> Optional.ofNullable(dto.getGroupKey()),
                                Collectors.reducing(BigDecimal.ZERO, Java8StreamDTO::getAmount, BigDecimal::add)));
        System.out.println(mapGroupSum);
        System.out.println();

    }

    private static List<Java8StreamDTO> init() {
        Object[][] initData = new Object[][]{
                {"1", new BigDecimal(1), LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 31), "1"},
                {"2", new BigDecimal(2), LocalDate.of(2021, 2, 1), LocalDate.of(2021, 2, 28), "2"},
                {"3", new BigDecimal(3), LocalDate.of(2021, 3, 1), LocalDate.of(2021, 3, 31), "1"},
                {"4", new BigDecimal(4), LocalDate.of(2021, 4, 1), LocalDate.of(2021, 4, 30), "2"},
                {"5", new BigDecimal(5), LocalDate.of(2021, 5, 1), LocalDate.of(2021, 5, 31), "1"},
                {"1", new BigDecimal(6), LocalDate.of(2021, 6, 1), LocalDate.of(2021, 6, 30), "2"}
        };

        List<Java8StreamDTO> listDTO = new ArrayList<>();
        for (Object[] initDatum : initData) {
            listDTO.add(new Java8StreamDTO(initDatum));
        }

        return listDTO;
    }

    private static String groupKey(Java8StreamDTO java8StreamDTO) {
        return java8StreamDTO.getGroupKey();
    }
}
