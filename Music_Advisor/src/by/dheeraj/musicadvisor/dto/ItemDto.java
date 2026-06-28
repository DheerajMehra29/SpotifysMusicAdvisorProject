package by.dheeraj.musicadvisor.dto;

import by.dheeraj.musicadvisor.domain.Item;

import java.util.List;

public record ItemDto(List<? extends Item> items, int totalPages, int currentPage) {

}
