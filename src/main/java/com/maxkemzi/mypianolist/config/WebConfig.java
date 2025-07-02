package com.maxkemzi.mypianolist.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.maxkemzi.mypianolist.piece.controller.PieceSortConverter;
import com.maxkemzi.mypianolist.user.piece.controller.UserPieceSortConverter;
import com.maxkemzi.mypianolist.user.piece.controller.UserPieceStatusConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver();

		pageableResolver.setSizeParameterName("limit");

		resolvers.add(pageableResolver);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new UserPieceStatusConverter());
		registry.addConverter(new PieceSortConverter());
		registry.addConverter(new UserPieceSortConverter());
		WebMvcConfigurer.super.addFormatters(registry);
	}
}
