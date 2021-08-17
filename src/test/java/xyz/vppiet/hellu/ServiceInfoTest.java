package xyz.vppiet.hellu;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
class ServiceInfoTest {

	@Test
	void clone_OneCommandInfo_DeepCopy() {
		ServiceInfo serviceInfo = new ServiceInfo("help", "Contains help commands");
		CommandInfo servicesCommandInfo = new CommandInfo("services", "Lists all services");
		serviceInfo.update(servicesCommandInfo);

		ServiceInfo clone = serviceInfo.clone();

		assertNotEquals(serviceInfo.getCommandInfos(), clone.getCommandInfos());
	}
}