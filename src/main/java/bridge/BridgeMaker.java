package bridge;

import bridge.domain.BridgeShape;
import bridge.exception.BridgeException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 다리의 길이를 입력 받아서 다리를 생성해주는 역할을 한다.
 */
public class BridgeMaker {
    private static final String VALIDATE_POSITIVE_EXCEPTION_MESSAGE = "음수로는 다리를 생성할 수 없습니다.";

    private final BridgeNumberGenerator bridgeNumberGenerator;

    public BridgeMaker(BridgeNumberGenerator bridgeNumberGenerator) {
        this.bridgeNumberGenerator = bridgeNumberGenerator;
    }

    /**
     * @param size 다리의 길이
     * @return 입력받은 길이에 해당하는 다리 모양. 위 칸이면 "U", 아래 칸이면 "D"로 표현해야 한다.
     */
    public List<String> makeBridge(int size) {
        validatePositive(size);
        return IntStream.range(0, size)
                .mapToObj(number -> BridgeShape.shape(bridgeNumberGenerator.generate()))
                .collect(Collectors.toList());
    }

    private void validatePositive(int size) {
        if (size < 0) {
            throw new BridgeException(VALIDATE_POSITIVE_EXCEPTION_MESSAGE);
        }
    }
}
