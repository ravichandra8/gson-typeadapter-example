/*
 * #%L
 * Gson TypeAdapter Example
 * %%
 * Copyright (C) 2012 - 2015 Java Creed
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.javacreed.examples.gson.part4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class BookTypeAdapter extends TypeAdapter<Book> {

  @Override
  public Book read(final JsonReader in) throws IOException {
    final Book book = new Book();

    in.beginObject();
    while (in.hasNext()) {
      switch (in.nextName()) {
      case "isbn":
        book.setIsbn(in.nextString());
        break;
      case "title":
        book.setTitle(in.nextString());
        break;
      case "authors":
        in.beginArray();
        final List<Author> authors = new ArrayList<>();
        while (in.hasNext()) {
          in.beginObject();
          final Author author = new Author();
          while (in.hasNext()) {
            switch (in.nextName()) {
            case "id":
              author.setId(in.nextInt());
              break;
            case "name":
              author.setName(in.nextString());
              break;
            }
          }
          authors.add(author);
          in.endObject();
        }
        book.setAuthors(authors.toArray(new Author[authors.size()]));
        in.endArray();
        break;
      }
    }
    in.endObject();

    return book;
  }

  @Override
  public void write(final JsonWriter out, final Book book) throws IOException {
    out.beginObject();
    out.name("isbn").value(book.getIsbn());
    out.name("title").value(book.getTitle());
    out.name("authors").beginArray();
    for (final Author author : book.getAuthors()) {
      out.beginObject();
      out.name("id").value(author.getId());
      out.name("name").value(author.getName());
      out.endObject();
    }
    out.endArray();
    out.endObject();
  }

}
