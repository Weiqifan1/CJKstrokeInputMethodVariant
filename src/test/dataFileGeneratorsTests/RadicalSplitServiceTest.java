package test.dataFileGeneratorsTests;



import main.Models.*;
import main.Models.sortingEnums.BasicStroke;
import main.Models.sortingEnums.Freq;
import main.Models.sortingEnums.InitialRadicals;
import main.dataFileGenerators.stokeMapGenerators.CharSmallService;
import main.dataFileGenerators.stokeMapGenerators.ConwayCodeService;
import main.dataFileGenerators.stokeMapGenerators.RadicalSplitService;
import main.dataFileGenerators.stokeMapGenerators.StrokeMapService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.*;
import java.util.stream.Collectors;

import java.util.List;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RadicalSplitServiceTest {


    private RadicalSplitService RSS;
    private CharSmallService SCS;
    private Map<String, Set<String>> radicalSplitMap;
    private Map<String, List<CharSmall>> sortByFreq;
    private Map<String, CJKChaaar> charToInfoCJKMap;


    @BeforeAll
    public void init(){
        Map<String, String> letters = ConwayCodeService.doubleLetters();
        System.out.println("CharSmallService Init()");
        RSS = new RadicalSplitService();
        SCS = new CharSmallService();
        radicalSplitMap = RSS.getRadicalSplitMap();

        StrokeMapService strokeMapService = new StrokeMapService();
        charToInfoCJKMap = strokeMapService.charToInfoCJKMap();

        RadicalExamples examples = new RadicalExamples();

        Parameters params = new Parameters(List.of(6,2),
                BasicStroke.DoubleStrokeOnly,
                Freq.JundaFirst,
                InitialRadicals.InitialRadicalsOnly,
                //plant   foot    bamboo  insect    tree   waterradical hand
                examples.testBasicRadicals(List.of("", "", "", "","", "", "A")),
                false);

        List<CharSmall> jundaSorted = SCS.generateChars(params, letters);
        sortByFreq = SCS.codeToCharSortetByFreq(jundaSorted);
    }

    //{Double@2241} 4299.0 -> ffhw 摞 埙 貢 埚 損 攔 頡 擻 捵 捰 塤 欯 擃 揋 歖 蛓 坱 捑
    // 壧 堁 抰 埧 鼚 撌 㙅 㙉 㙗 㠬 㨛 㩧 㰻 䠭 垻 墤 抧 撔 擑 踈 𨁈
    //qy 啃 夂 夊 //{Double@2273} 3345.0 -> qy 啃 芍 芨 芄 趿 趵 芃 䒘 䒟 夂 夊 芕
    //{Double@2241} 4299.0 -> ffhw 摞 埙 貢 茛 埚 菉 芵 損 攔 頡 擻 捵 捰 塤 欯
    //挋   載 㧳䳲鋬鸷挚势紥贽蛰㐝垫踅裚梊䋢䭁烲銴娎蜇焎悊埑逝㿱哲誓硩乴晢䀸㔼

    @Test
    void generateCharSmall_jundaFirst() {



    }
}
