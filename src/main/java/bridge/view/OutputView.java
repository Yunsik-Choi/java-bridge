package bridge.view;

import bridge.domain.Bridge;
import bridge.domain.BridgeGameResult;
import bridge.domain.BridgeShape;
import bridge.domain.TryCount;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 사용자에게 게임 진행 상황과 결과를 출력하는 역할을 한다.
 */
public class OutputView {
    private static final String GAME_SUCCESS_GUIDE = "게임 성공 여부: ";
    private static final String SUCCESS = "성공";
    private static final String FAIL = "실패";
    private static final String TRY_COUNT_GUIDE = "총 시도한 횟수: ";
    private static final String PRINT_RESULT_GUIDE = "최종 게임 결과";
    private static final String MOVE_SUCCESS = " O ";
    private static final String MOVE_FAIL = " X ";
    private static final String MOVE_NOT_SELECT = "   ";
    private static final String BRIDGE_DELIMITER = "|";
    private static final String BRIDGE_END = "]";
    private static final String BRIDGE_START = "[";
    private static final String NEW_LINE = "\n";

    /**
     * 현재까지 이동한 다리의 상태를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printMap(BridgeGameResult result) {
        StringBuilder printMap = new StringBuilder();
        printMap.append(printMapFormat(result)).append(NEW_LINE);
        System.out.println(printMap);
    }

    /**
     * 게임의 최종 결과를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printResult(BridgeGameResult bridgeGameResult, TryCount tryCount) {
        StringBuilder result = new StringBuilder();
        result.append(PRINT_RESULT_GUIDE).append(NEW_LINE);
        result.append(printMapFormat(bridgeGameResult)).append(NEW_LINE).append(NEW_LINE);
        result.append(printGameSuccessFormat(bridgeGameResult)).append(NEW_LINE);
        result.append(printTryCountFormat(tryCount));
        System.out.println(result);
    }

    public String printMapFormat(BridgeGameResult result) {
        Bridge bridge = result.bridge();
        List<BridgeShape> bridgeShapes = bridge.bridgeShapes();
        List<Boolean> booleans = result.attemptsResult();
        return String.join(NEW_LINE, upSideFormat(bridgeShapes, booleans), downSideFormat(bridgeShapes, booleans));
    }

    private String upSideFormat(List<BridgeShape> bridgeShapes, List<Boolean> booleans) {
        List<String> upSidesTexts = IntStream.range(0, booleans.size())
                .mapToObj(index -> moveUpSideFormat(booleans.get(index), bridgeShapes.get(index)))
                .collect(Collectors.toList());
        return bridgeFormat(upSidesTexts);
    }

    private String moveUpSideFormat(boolean move, BridgeShape bridgeShape) {
        if (move && bridgeShape == BridgeShape.UP) {
            return MOVE_SUCCESS;
        }
        if (!move && bridgeShape == BridgeShape.DOWN) {
            return MOVE_FAIL;
        }
        return MOVE_NOT_SELECT;
    }

    private String downSideFormat(List<BridgeShape> bridgeShapes, List<Boolean> booleans) {
        List<String> downSideTexts = IntStream.range(0, booleans.size())
                .mapToObj(index -> moveDownSideFormat(booleans.get(index), bridgeShapes.get(index)))
                .collect(Collectors.toList());
        return bridgeFormat(downSideTexts);
    }

    private String moveDownSideFormat(boolean move, BridgeShape bridgeShape) {
        if (move && bridgeShape == BridgeShape.DOWN) {
            return MOVE_SUCCESS;
        }
        if (!move && bridgeShape == BridgeShape.UP) {
            return MOVE_FAIL;
        }
        return MOVE_NOT_SELECT;
    }

    private String bridgeFormat(List<String> moveSideTexts) {
        return BRIDGE_START + String.join(BRIDGE_DELIMITER, moveSideTexts) + BRIDGE_END;
    }

    public String printGameSuccessFormat(BridgeGameResult bridgeGameResult) {
        if (bridgeGameResult.isSuccess()) {
            return GAME_SUCCESS_GUIDE + SUCCESS;
        }
        return GAME_SUCCESS_GUIDE + FAIL;
    }

    public String printTryCountFormat(TryCount tryCount) {
        return TRY_COUNT_GUIDE + tryCount.count();
    }
}
