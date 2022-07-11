package com.qewby.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sqlite.SQLiteException;

import com.qewby.network.dto.GroupDto;
import com.qewby.network.service.implementation.DefaultGroupService;


public class GroupDaoTest {

    private static final DefaultGroupService groupService = new DefaultGroupService();

    private static final String testDatabaseName = "test.db";

    @BeforeClass
    public static void createTestDatabase() throws SQLException, IOException {
        Application application = new Application(8080);
        application.initializeDatabase(testDatabaseName);

        GroupDto vegetables = new GroupDto();
        String name = "Vegetables";
		vegetables.setName(name);
		groupService.addGroup(vegetables);

		GroupDto fruits = new GroupDto();
        name = "Fruits";
		fruits.setName(name);
		fruits.setDescription("Bananas , apples ...");
		groupService.addGroup(fruits);
    }

    @Test
    public void afterUpdateDataMustBeUpdated() throws SQLException {

        String afterName = "Meat";
        int groupIndex = 0;
        List<GroupDto> beforeUpdate = groupService.getAllGroups();
        GroupDto groupDto = beforeUpdate.get(groupIndex).withName(afterName);
        int rowCount = groupService.updateGroup(groupDto);
        assertEquals(1, rowCount);
        List<GroupDto> afterUpdate = groupService.getAllGroups();
        assertNotEquals(beforeUpdate.get(groupIndex), afterUpdate.get(groupIndex));
        assertEquals(afterName, afterUpdate.get(groupIndex).getName());
    }

    @Test
    public void afterDeleteOneGroupsNumberMustBeSmaller() throws SQLException {

        List<GroupDto> beforeDelete = groupService.getAllGroups();
        assertEquals(2, beforeDelete.size());
        int deleteIndex = 1;

        int rowCount = groupService.deleteGroupById(beforeDelete.get(0).getId());
        assertEquals(1, rowCount);

        List<GroupDto> afterDelete = groupService.getAllGroups();
        assertEquals(1, afterDelete.size());
        assertEquals(beforeDelete.get(deleteIndex).getId(), afterDelete.get(deleteIndex - 1).getId());
        assertEquals(beforeDelete.get(deleteIndex).getName(), afterDelete.get(deleteIndex - 1).getName());
        assertEquals(beforeDelete.get(deleteIndex).getDescription(), afterDelete.get(deleteIndex - 1).getDescription());
    }

    @Test
    public void groupReturnedByIdMustBeSimilarToGroupFromListWithSameId() throws SQLException {
        List<GroupDto> allGroups = groupService.getAllGroups();
        GroupDto expected = allGroups.get(0);
        GroupDto actual = groupService.getGroupById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void groupReturnedByNameMustBeSimilarToGroupFromListWithSameName() throws SQLException {
        List<GroupDto> allGroups = groupService.getAllGroups();
        GroupDto expected = allGroups.get(0);
        GroupDto actual = groupService.getGroupByName(expected.getName());
        assertEquals(expected, actual);
    }

    @Test
    public void mustReturnZeroIfUpdateIdNotExists() throws SQLException {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(1000);
        groupDto.setName("name");
        int rowCount = groupService.updateGroup(groupDto);
        assertEquals(0, rowCount);
    }

    @Test
    public void mustReturnZeroIfDeleteIdNotExists() throws SQLException {
        int rowCount = groupService.deleteGroupById(1000);
        assertEquals(0, rowCount);
    }

    @Test(expected = SQLException.class)
    public void throwIfInsertNameAlreadyExists() throws SQLException {
		assertThatExceptionOfType(SQLiteException.class).isThrownBy(()->
		{
			List<GroupDto> all = groupService.getAllGroups();
			groupService.addGroup(all.get(0));
		});

    }

    @Test
    public void noValueIfIdNotExists() throws SQLException {
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(()->
		{
			GroupDto groupDto = groupService.getGroupById(1000);
		});
    }

    @Test
    public void noValueIfNameNotExists() throws SQLException {
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(()->
		{
			GroupDto groupDto = groupService.getGroupByName("HLSDFJLS");
		});
    }

    @AfterClass
    public static void deleteTestDatabase() {
        File file = new File(testDatabaseName);
        file.delete();
    }
}
