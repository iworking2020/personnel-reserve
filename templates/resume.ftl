<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="UTF-8">
        <style type="text/css">
            * {
                font-family: CenturyGothic;
            }
            h1, h2, h3, h4, h5 {
                margin: 0;
                font-weight: normal;
            }
            .param-name {
                margin-right: 5px;
            }
            #image-container {
                padding: 0 15px 15px 0;
                width: 200px;
            }
            .separator-middle {
                margin: 10px 0 0 0;
            }
            .separator-big {
                margin: 20px 0 0 0;
            }
            #photo {
                width: 180px;
            }
        </style>
    </head>
    <body>
        <table width="100%">
            <tr>
                <td id="image-container">
                    <#if resume.photo??>
                        <img id="photo" src="${imageUtil.convertImageToBase64(resume.photo.image)}"/>
                    <#else>
                        <img id="photo" src="${imageUtil.convertImageToBase64(imageUtil.getDefaultResumeImage())}"/>
                    </#if>
                </td>
                <td>
                    <div>
                        <#if resume.profile??>
                            <h3><b>${resume.profile.lastName} ${resume.profile.firstName} ${resume.profile.middleName}</b></h3>
                        <#else>
                            <h3><b>Ф.И.О. не указано</b></h3>
                        </#if>
                    </div>
                    <div class="separator-middle"></div>
                    <div>
                        <h4 class="param-name">
                            <span>Должность:</span>
                            <#if resume.profession?has_content>
                                <span>${resume.profession}</span>
                            <#else>
                                <span>не указана</span>
                            </#if>
                        </h4>

                    </div>
                    <div>
                        <span class="param-name">Профобласть:</span>
                        <#if resume.profField??>
                            <span>${resume.profField.nameView.name}</span>
                        <#else>
                            <span>не указана</span>
                        </#if>
                    </div>
                    <div>
                        <span class="param-name">График:</span>
                        <#if resume.workType??>
                            <span>${resume.workType.nameView.name}</span>
                        <#else>
                            <span>не указан</span>
                        </#if>
                    </div>
                    <div class="separator-middle"></div>
                    <div>
                        <span class="param-name"><span>Желаемый уровень дохода:</span></span>
                        <#if resume.wage??>
                            <span>${resume.wage.count}</span>
                            <span> ${resume.wage.currency.nameView.name}</span>
                            <#if resume.wage.period??>
                                <span> ${resume.wage.period.nameView.name}</span>
                            </#if>
                        <#else>
                            <span>не указан</span>
                        </#if>
                    </div>
                    <div class="separator-middle"></div>
                    <div>
                        <span><b>Контактная информация:</b></span>
                    </div>
                    <div class="row">
                        <span class="param-name">тел.:</span>
                        <#if resume.numberPhone??>
                            <#if resume.numberPhone.fullNumber?has_content>
                                <span>${resume.numberPhone.fullNumber}</span>
                            <#else>
                                <span>не указан</span>
                            </#if>
                        <#else>
                            <span>не указан</span>
                        </#if>
                    </div>
                    <div>
                        <span class="param-name">емайл:</span>
                        <#if resume.email?has_content>
                            <span>${resume.email}</span>
                        <#else>
                            <span>не указан</span>
                        </#if>
                    </div>
                </td>
            </tr>
        </table>
        <table width="100%">
            <#list resume.experienceHistoryList as experienceHistory>
                <tr>
                    <td>
                        <div class="separator-big"></div>
                        <span><b>Опыт работы: </b></span>
                        <#if experienceHistory.dateStart??>
                            <span>${dateTimeFormatter.format(experienceHistory.dateStart)} - </span>
                        </#if>
                        <#if experienceHistory.dateEnd??>
                            <span>${dateTimeFormatter.format(experienceHistory.dateEnd)}</span>
                        <#else>
                            <span>по сегодняшний день</span>
                        </#if>
                    </td>
                </tr>
                <tr>
                    <td>
                        <#if experienceHistory.description?has_content>
                            <span style="white-space: pre-wrap;">${experienceHistory.description}</span>
                        <#else>
                            <span>без описания</span>
                        </#if>
                    </td>
                </tr>
            </#list>
        </table>
        <table width="100%">
            <#list resume.learningHistoryList as learningHistory>
                <tr>
                    <td>
                        <div class="separator-big"></div>
                        <span><b>Образование: </b></span>
                        <#if learningHistory.education??>
                            <span>${learningHistory.education.nameView.name}</span>
                        <#else>
                            <span>не указано</span>
                        </#if>
                    </td>
                </tr>
                <tr>
                    <td>
                        <#if learningHistory.description?has_content>
                            <span style="white-space: pre-wrap;">${learningHistory.description}</span>
                        <#else>
                            <span>без описания</span>
                        </#if>
                    </td>
                </tr>
            </#list>
        </table>
    </body>
</html>
