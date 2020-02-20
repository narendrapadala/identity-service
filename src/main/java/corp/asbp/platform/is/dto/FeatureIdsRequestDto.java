package corp.asbp.platform.is.dto;

import java.util.List;

import lombok.Data;

@Data
public class FeatureIdsRequestDto {
	private List<Long> featureIds;
}
